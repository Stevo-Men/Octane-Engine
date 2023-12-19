package theprojekt;

import doctrina.*;
import doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import static theprojekt.SoundEffect.NPC_ATTACK;
import static theprojekt.SoundEffect.NPC_SCREAMING;

public class Npc extends MovableEntity{

    private static final String SPRITE_PATH = "images/enemy1.png";
    private static final String ATTACK_EFFECT_PATH = "images/enemy_Attack.png";
    private static final String IMPACT_PATH = "images/enemy_impact.png";
    private static final int ANIMATION_SPEED = 8;
    protected java.util.Map<Direction, Image[]> directionFramesMap = new HashMap<>();
    private BufferedImage spriteImpact;
    private BufferedImage spriteSheet;
    private BufferedImage attackEffect;
    private int currentAnimationFrame = 1;
    private int nextFrame = ANIMATION_SPEED;
    private int pathNumber = 1;
    private int health = 50;
    private int cooldown = 0;
    private int pathCooldown = 0;
    private int parameterX,parameterY;
    private final int parameterWidth = 130;
    private final int parameterHeight = 200;
    private boolean isDamaged = false;
    private boolean detectedFx = false;
    public boolean isAttacking = false;
    protected java.util.Map<Direction, Direction.TriConsumer<Integer, Integer, Integer>> directionCalculations = new HashMap<>();
    private Camera camera;
    private final SoundEffect npcScreaming = NPC_SCREAMING;
    private final SoundEffect npcAttack = NPC_ATTACK;
    private int path;





    public Npc(int x, int y, int path) {
        this.x = x;
        this.y = y;
        this.path = path;
        setSpeed(1);
        setDimension(32, 32);
        camera = new Camera();
        load();

    }

    private void load() {
       spriteSheet = loadNPCimage(SPRITE_PATH, spriteSheet);
       attackEffect = loadNPCimage(ATTACK_EFFECT_PATH, attackEffect);
        loadAnimationFrames();
    }

    public void update(Player player) {
        super.update();
        handleAnimationEnemy();
        coolDown();
        takeAction(player);


    }

    private void coolDown() {
        cooldown--;
        pathCooldown--;
        if (cooldown < 0) {
            cooldown = 0;
        }
    }

    private void takeAction(Player player) {
        if (isChasing(player)) {
            setSpeed(2);
            chase(player);
            npcScreaming.play();
        } else if (path == 1) {
            setSpeed(1);
            trajectory();
        } else if (path == 2) {
            setSpeed(1);
            trajectoryLinear();
        } else {
            setSpeed(1);
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

    private void trajectoryLinear() {
        if (pathNumber == 1) {
            move(Direction.RIGHT);
            if (pathCooldown <= 0 || !this.hasMoved())  {
                pathNumber++;
                pathCooldown = 100;
            }
        } else if (pathNumber == 2) {
            move(Direction.LEFT);
            if (pathCooldown <= 0 || !this.hasMoved()) {
                pathNumber = 1;
                pathCooldown = 100;
            }
        }
    }



    public void chase(Player player) {
        //Pris sur ChatGPT
        int enemyX = this.x;
        int enemyY = this.y;
        int playerX = player.getX();
        int playerY = player.getY()-16;
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


    public void draw(Canvas canvas,  boolean isNear) {

       if (isNear) {
           drawHealthEnemy(canvas);
           drawEnemyImage(canvas);
           canvas.drawString(" " + cooldown, x - 10, y - 10, Color.GREEN);
       } else {
           canvas.drawCircle(x - 32, y - 32, 50, new Color(142, 125, 255, 65));
       }
    }


    public void drawAttackEffect(Canvas canvas, Player player) {
        canvas.drawImage(attackEffect, player.getX(), player.getY());
    }


    private void drawHealthEnemy(Canvas canvas) {
        canvas.drawHealthNPC(x-7,y-1,52,8,Color.BLACK);
        canvas.drawHealthNPC(x-6,y,50,6,Color.RED);
        canvas.drawHealthNPC(x-6 , y ,this.health, 6, Color.GREEN);
    }
    @Override
    public void draw(Canvas canvas) {

    }

    private BufferedImage loadNPCimage(String path, BufferedImage imageName) {
        try {
            imageName = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imageName;
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

    protected void drawEnemyImage(Canvas canvas) {
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
        cooldown = 40;
        player.playerHealth -= 10;
        npcAttack.play();
    }

    public void isTouched(Knife knife) {
        this.health -= knife.damage;
        isTouched();
    }

    public boolean isTouched() {
        return isDamaged;
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
        } else {
           Rectangle verticalParameter =  new Rectangle(x, y, parameterHeight, parameterWidth);
        }
    }

}
