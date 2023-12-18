package theprojekt;

import doctrina.*;
import doctrina.Canvas;

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
    private int knifeMunition = 5;
    public int playerHealth = 100;
    private int cooldown = 0;
    protected final int maxHealth = 100;

    public Player(MovementController controller) {
        super(controller);
        setDimension(32, 32);
        setSpeed(2);
        camera = new Camera();
        visualEffect = new VisualEffect(this);
        animatedEntity = new AnimatedEntity(SPRITE_PATH,this.getWidth(),this.getHeight());
        load();
    }

    public Knife throwKnife() {
        cooldown = 50;
        knifeMunition--;
        return new Knife(this);
    }


    public boolean canThrow() {
        return cooldown == 0 && knifeMunition > 0;
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


        canvas.drawRectangle(this.getBounds().x,this.getBounds().y,this.getBounds().width,this.getBounds().height,new Color(255, 83, 83, 81));
        canvas.drawString(" " + x + " " + y, x, y, Color.RED);
    }

    private void load() {
        animatedEntity.loadSpriteSheet(SPRITE_PATH);

    }








}
