package theprojekt;

import doctrina.*;
import doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Npc extends MovableEntity{

    private static final String SPRITE_PATH = "images/enemy1.png";
    private static final int ANIMATION_SPEED = 8;
    protected java.util.Map<Direction, Image[]> directionFramesMap = new HashMap<>();

    private BufferedImage spriteSheet;
    private int currentAnimationFrame = 1;
    private int nextFrame = ANIMATION_SPEED;
    private int pathNumber = 1;
    private int health = 50;
    private int cooldown = 0;
    private int pathCooldown = 0;
    private int parameterX;
    private int parameterY;
    private int parameterWidth = 130;
    private int parameterHeight = 200;
    protected java.util.Map<Direction, Direction.TriConsumer<Integer, Integer, Integer>> directionCalculations = new HashMap<>();
    private Camera camera;





    public Npc(int x, int y) {
        this.x = x;
        this.y = y;
        setSpeed(1);
        setDimension(32, 32);
        camera = new Camera();
        load();

    }

    public void update(Player player) {
        super.update();

           x = camera.translateX(this.x);
           y = camera.translateY(this.y);



        cooldown--;
        pathCooldown--;
        if (cooldown < 0) {
            cooldown = 0;
        }



        handleAnimationEnemy();

            if (isChasing(player)) {
                chase(player);
            } else {
                trajectory();
            }

    }





    private void trajectory() {
        if (pathNumber == 1) {
            move(Direction.RIGHT);
            if (pathCooldown <= 0 || !this.hasMoved())  {
                pathNumber++;
                pathCooldown = 100;
            }
        } else if (pathNumber == 2) {
            move(Direction.DOWN);
            if (pathCooldown <= 0 || !this.hasMoved()) {
                pathNumber++;
                pathCooldown = 100;
            }
        } else if (pathNumber == 3) {
            move(Direction.LEFT);
            if (pathCooldown <= 0 || !this.hasMoved()) {
                pathNumber++;
                pathCooldown = 100;
            }
        } else if (pathNumber == 4) {
            move(Direction.UP);
            if (pathCooldown <= 0 || !this.hasMoved()) {
                pathNumber = 1;
                pathCooldown = 100;
            }
        }
    }




    public void chase(Player player) {
        int enemyX = this.x;
        int enemyY = this.y;
        int playerX = player.getX();
        int playerY = player.getY();
        int dx = playerX - enemyX;
        int dy = playerY - enemyY;
        double angle = Math.atan2(dy, dx);

        if (angle >= -Math.PI / 4 && angle < Math.PI / 4) {
            move(Direction.RIGHT);
        } else if (angle >= Math.PI / 4 && angle < 3 * Math.PI / 4) {
            move(Direction.DOWN);
        } else if ((angle >= 3 * Math.PI / 4 && angle <= Math.PI) || (angle >= -Math.PI && angle < -3 * Math.PI / 4)) {
            move(Direction.LEFT);
        } else {
            move(Direction.UP);
        }
    }


    public int getHealth() {
        return health;
    }


    public void draw(Canvas canvas, Camera camera) {
        int drawX = x - camera.translateX(this.x); // Adjusted for camera
        int drawY = y - camera.translateX(this.y);  // Adjusted for camera
        canvas.drawRectangle(x  , y, width, height, new Color(255, 226, 40, 20));
        canvas.drawRectangle(getBounds().x, getBounds().y, this.getBounds().width, getBounds().height, new Color(40, 255, 86, 20));
        canvas.drawString(" " + x + " " + y ,x ,y ,Color.RED);
        drawHealthEnemy(canvas,x,y);
        drawEnemyImage(canvas, x,y);
        canvas.drawString(" " + pathCooldown, x - 10, y - 10,Color.GREEN);
        canvas.drawString(" " + pathNumber, x - 20, y - 20,Color.GREEN);
    }


    private void drawHealthEnemy(Canvas canvas, int cameraX, int cameraY) {
        canvas.drawHealthNPC(x-7,y-1,52,8,Color.BLACK);
        canvas.drawHealthNPC(x-6,y,50,6,Color.RED);
        canvas.drawHealthNPC(x-6 , y ,this.health, 6, Color.GREEN);
    }
    @Override
    public void draw(Canvas canvas) {

    }

    private void load() {
        loadSpriteSheet();
        loadAnimationFrames();
    }

    private void loadSpriteSheet() {
        try {
            spriteSheet = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    protected void loadAnimationFrames() {
        directionFramesMap.put(Direction.DOWN, loadFrames(0));
        directionFramesMap.put(Direction.LEFT, loadFrames(32));
        directionFramesMap.put(Direction.RIGHT, loadFrames(64));
        directionFramesMap.put(Direction.UP, loadFrames(96));
    }

    protected Image[] loadFrames(int startY) {
        Image[] frames = new Image[3];
        frames[0] = spriteSheet.getSubimage(0, startY, width, height);
        frames[1] = spriteSheet.getSubimage(32, startY, width, height);
        frames[2] = spriteSheet.getSubimage(64, startY, width, height);
        return frames;
    }

    protected void handleAnimationEnemy() {
        if (!hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= directionFramesMap.get(getDirection()).length) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1;
        }
    }

    protected void drawEnemyImage(Canvas canvas, int cameraX, int cameraY) {
        Direction direction = getDirection();
        Image[] frames = directionFramesMap.get(direction);


        if (frames != null) {
            canvas.drawImage(frames[currentAnimationFrame], x, y);
        }
    }
    public boolean isChasing(Player player) {
        return parameterDirection().intersects(player.getBounds());
    }



    public boolean canAttack(Player player) {
        return (this.getBounds().intersects(player.getBounds()) && cooldown == 0);
    }

    public void attack(Player player) {
        cooldown = 25;
        player.playerHealth -= 10;
    }

    public void isTouched(Knife knife) {
        this.health -= knife.damage;
    }


    public Rectangle parameterDirection() {

        Direction direction = this.getDirection();

        directionCalculations.put(Direction.RIGHT, (x, y, width) -> {
            parameterX = x + 10;
            parameterY = y - 90;
            detectionParameter(parameterX, parameterY, true);
        });

        directionCalculations.put(Direction.LEFT, (x, y, width) -> {
            parameterX = x - 110;
            parameterY = y - 80;
            detectionParameter(parameterX, parameterY, true);
        });

        directionCalculations.put(Direction.DOWN, (x, y, width) -> {
            parameterX = x - 80;
            parameterY = y + 8;
            detectionParameter(parameterX, parameterY, false);
        });

        directionCalculations.put(Direction.UP, (x, y, width) -> {
            parameterX = x - 75;
            parameterY = y - 100;
            detectionParameter(parameterX, parameterY, false);
        });

        directionCalculations.getOrDefault(direction, (x, y, width) -> {
        }).accept(this.getX(), this.getY(), this.getWidth());

        return new Rectangle(parameterX, parameterY, parameterWidth, parameterHeight) {
        };
    }

    public void detectionParameter(int x, int y, boolean horizontal) {
        if (horizontal) {
           Rectangle horizontalParameter = new Rectangle(x, y, parameterWidth, parameterHeight);
           // canvas.drawDetectionRect(horizontalParameter, new Color(99, 144, 255, 137));
        } else {
           Rectangle verticalParameter =  new Rectangle(x, y, parameterHeight, parameterWidth);
          //  canvas.drawDetectionRect(verticalParameter , new Color(99, 144, 255, 137));
        }
    }


}
