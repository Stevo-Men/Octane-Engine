package theprojekt;

import doctrina.Canvas;
import doctrina.CollidableRepository;
import doctrina.Direction;
import doctrina.MovableEntity;

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

    private int speed;
    private boolean path1 = true;
    private boolean path2 = false;
    private boolean path3 = false;
    private boolean path4 = false;
    private int health = 50;
    private Knife knife;
    private Map map;
    private Camera camera;
    private int cameraX;
    private int cameraY;



    public Npc(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 1;
        setDimension(32, 32);
        camera = new Camera();
        cameraX = 0;
        cameraY = 0;
        load();
        CollidableRepository.getInstance().registerEntity(this);
    }

    public void update(int translatedX, int translatedY, Player player) {
        super.update();
        if (hasMoved()) {
            x -= translatedX;
            y -= translatedY;
        }

        handleAnimationEnemy();
        chase(player);

    }



    public void isTouched(Knife knife) {
        this.health -= knife.damage;
    }

    private void trajectory() {
        if (path1) {
            move(Direction.RIGHT);
            if (y >= 500) {
                path1 = false;
                path2 = true;
            }
        } else if (path2) {
            move(Direction.DOWN);
            if (x <= 100) {
                path2 = false;
                path3 = true;
            }
        } else if (path3) {
            move(Direction.LEFT);
            if (y <= 200) {
                path3 = false;
                path4 = true;
            }
        } else if (path4) {
            move(Direction.UP);
            if (x >= 400) {
                path4 = false;
                path1 = true;
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



    public void draw(Canvas canvas, int cameraX, int cameraY) {
        canvas.drawRectangle(x - cameraX  , y - cameraY, width, height, new Color(255, 226, 40, 20));
        canvas.drawRectangle(x, y, getBounds().width, getBounds().height, new Color(40, 255, 86, 20));
        canvas.drawString(" " + x + " " + y ,x - cameraX,y - cameraY,Color.RED);
        drawHealthEnemy(canvas,x,y);
        drawEnemyImage(canvas, x - cameraX,y - cameraY);
    }


    private void drawHealthEnemy(Canvas canvas, int cameraX, int cameraY) {
        canvas.drawRectangle(x - cameraX, y - cameraY,50, 10, Color.RED);
        canvas.drawRectangle(x - cameraX, y - cameraY,50, 10, Color.GREEN);
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
            //  System.out.println("Player Coordinates: x = " + (int) this.position.x + ", y = " + (int) this.position.y);

        }
    }




}
