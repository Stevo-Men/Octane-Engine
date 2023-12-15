package theprojekt;

import doctrina.Canvas;
import doctrina.ControllableEntity;
import doctrina.Direction;
import doctrina.MovementController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Player extends ControllableEntity {

    private static final String SPRITE_PATH = "images/player2.png";

    protected Map<Direction, Image[]> directionFramesMap = new HashMap<>();

    private BufferedImage spriteSheet;
    private int currentAnimationFrame = 1;
    private final int ANIMATION_SPEED = 8;
    private int nextFrame = ANIMATION_SPEED;
    Camera camera;
    private VisualEffect visualEffect;
    private AnimatedEntity animatedEntity;

    public int playerHealth = 100;
    private int cooldown = 0;
    protected final int maxHealth = 100;

    public Player(MovementController controller) {
        super(controller);
        setDimension(32, 32);
        setSpeed(2);

        camera = new Camera();
        visualEffect = new VisualEffect(this);
        animatedEntity = new AnimatedEntity(SPRITE_PATH, x, y, 32, 32, 2);
        load();
    }

    public Knife throω() {
        cooldown = 50;
        return new Knife(this);
    }

    public boolean canThrow() {
        return cooldown == 0;
    }

    @Override
    public void update() {
        super.update();

        moveWithController();
        animatedEntity.update(this);

        cooldown--;
        if (cooldown < 0) {
            cooldown = 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        animatedEntity.drawCharacterImage(canvas, 0, 0, this);;
        visualEffect.drawPlayerLight(canvas);

        canvas.drawString(" " + x + " " + y, x, y, Color.RED);
    }

    private void load() {
        animatedEntity.loadSpriteSheet(SPRITE_PATH);

    }








}
