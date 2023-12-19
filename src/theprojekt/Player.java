package theprojekt;

import doctrina.*;
import doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Player extends ControllableEntity {

    private static final String SPRITE_PATH = "images/player2.png";

    protected Map<Direction, Image[]> directionFramesMap = new HashMap<>();


    private int currentAnimationFrame = 1;
    private final int ANIMATION_SPEED = 8;
    private int nextFrame = ANIMATION_SPEED;
    Camera camera;
    private VisualEffect visualEffect;
    private AnimatedEntity animatedEntity;
    int knifeMunition = 10;
    public int playerHealth = 1000000;
    private int cooldown = 0;
    private boolean isAlive = true;
    public boolean detectedState = false;
    protected final int maxHealth = 100;
    private SoundEffect soundEffect;
    private boolean isDashing = false;
    private int dashDuration = 50;
    private int dashCooldown = 50;
       private int dashTimer = 0;
    private int currentSpeed = 5;
    public Ellipse2D playerVision;

    public Player(MovementController controller) {
        super(controller);
        setDimension(32, 32);
        setSpeed(2);
        camera = new Camera();
        visualEffect = new VisualEffect(this);
        animatedEntity = new AnimatedEntity(SPRITE_PATH,this.getWidth(),this.getHeight());

        load();
    }

    public int getKnifeMunition() {
        return knifeMunition;
    }

    public Knife throwKnife() {
        cooldown = 50;
        knifeMunition--;
        soundEffect.KNIFE_THROW.play();
        return new Knife(this);
    }


    public boolean canThrow() {
        return cooldown == 0 && knifeMunition > 0;
    }
    public boolean canDash() {
        if (!isDashing && dashCooldown <= 0) {
            isDashing = true;
            dashTimer = 0;
        }
        return dashCooldown == 0;
    }

    public void dash() {

        if (isDashing) {
        float dashProgress = (float) 100 / dashDuration;

        if (getDirection() == Direction.RIGHT) {
            x += (int) (currentSpeed * dashProgress);
        } else if (getDirection() == Direction.LEFT) {
            x -= (int) (currentSpeed * dashProgress);
        } else if (getDirection() == Direction.DOWN) {
            y += (int) (currentSpeed * dashProgress);
        } else if (getDirection() == Direction.UP) {
            y -= (int) (currentSpeed * dashProgress);
        }
        dashTimer++;

            if (dashTimer >= 10) {
                isDashing = false;
                dashCooldown = 50;
            }
        }

    }

    @Override
    public void update() {
        super.update();

        moveWithController();
        animatedEntity.update(this);
        playerVision = visualEffect.getCircle();
        dashCooldown--;
        if (dashCooldown < 0) {
            dashCooldown = 0;
        }

        cooldown--;
        if (cooldown < 0) {
            cooldown = 0;
        }

        if (playerHealth <= 0) {
            isAlive = false;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        animatedEntity.drawCharacterImage(canvas, 0, 0, this);;
        visualEffect.drawPlayerLight(canvas);
    }

    public void drawDebuginfo(Canvas canvas) {
        canvas.drawString(" " + isDetected(), x-60, y-60, Color.WHITE);
        canvas.drawString("x: " + x + " y: " + y, x - 50, y - 50, Color.WHITE);
    }

    private void load() {
        animatedEntity.loadSpriteSheet(SPRITE_PATH);

    }

    public boolean isDetected() {
        return detectedState;
    }


    public boolean stillAlive() {
            return isAlive;
        }




}
