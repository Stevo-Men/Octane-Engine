package theprojekt;

import doctrina.Canvas;
import doctrina.ControllableEntity;
import doctrina.Direction;
import doctrina.MovementController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Player extends ControllableEntity {

    private static final String SPRITE_PATH = "images/player2.png";
    private static final int ANIMATION_SPEED = 8;
    protected Map<Direction, Image[]> directionFramesMap = new HashMap<>();

    private BufferedImage spriteSheet;
    private int currentAnimationFrame = 1;
    private int nextFrame = ANIMATION_SPEED;
    Camera camera;

    private int lightX;
    private int lightY;
    private int lightWidth = 20;
    private int lightHeight = 64;
    public int playerHealth = 100;
    private int cooldown = 0;
    protected final int maxHealth = 100;


    public Player(MovementController controller) {
        super(controller);
        setDimension(32, 32);
        setSpeed(3);
        load();
        camera = new Camera();

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
        handleAnimation();

        cooldown--;
        if (cooldown < 0) {
            cooldown = 0;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        drawPlayerImage(canvas, 0, 0);
        drawPlayerLight(canvas);


        canvas.drawString(" " + x + " " + y,x,y,Color.RED);

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

    protected void handleAnimation() {
        if (hasMoved()) {
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

    protected void drawPlayerImage(Canvas canvas, int translatedX, int translatedY) {
        Direction direction = getDirection();
        Image[] frames = directionFramesMap.get(direction);

        if (frames != null) {
            canvas.drawImage(frames[currentAnimationFrame], x - translatedX, y - translatedY);
        }
    }

    public void drawPlayerLight(Canvas canvas) {
        Direction direction = getDirection();

        int offsetX = 6;
        int offsetY = 15 - 2;


        directionCalculations.put(Direction.RIGHT, (x, y, width) -> {
            lightX = x + this.getWidth() - 16;
            lightY = y + offsetY;

            addDarkTint(canvas, 0.5f,false);


        });
        directionCalculations.put(Direction.LEFT, (x, y, width) -> {
            lightX = x - 48;
            lightY = y + offsetY;
            addDarkTint(canvas, 0.5f,false);

        });
        directionCalculations.put(Direction.DOWN, (x, y, width) -> {
            lightX = x + offsetX;
            lightY = y + this.getHeight() + 1;
            addDarkTint(canvas, 0.5f,true);

        });
        directionCalculations.put(Direction.UP, (x, y, width) -> {
            lightX = x + offsetX;
            lightY = y - 50;
            addDarkTint(canvas, 0.5f,true);

        });

        directionCalculations.getOrDefault(direction, (x, y, width) -> {}).accept(this.getX(), this.getY(), this.getWidth());


    }



    public void addDarkTint(Canvas canvas, float darkTintOpacity, boolean vertical) {

            Graphics2D graphics = canvas.getGraphics();
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, darkTintOpacity));

            int drawX = camera.translateX(camera.getX());
            int drawY = camera.translateY(camera.getY());
            int canvasWidth = 1000;
            int canvasHeight = 600;

            Rectangle rect = new Rectangle(drawX, drawY, canvasWidth, canvasHeight);
            Ellipse2D ovalVertical = new Ellipse2D.Float(lightX, lightY, lightWidth, lightHeight);
            Ellipse2D ovalHorizontal = new Ellipse2D.Float(lightX, lightY, lightHeight, lightWidth);
            Area area = new Area(rect);

            if (vertical) {
                area.subtract(new Area(ovalVertical));

            } else {
                area.subtract(new Area(ovalHorizontal));
            }

                graphics.setClip(area);
                graphics.setColor(Color.BLACK);
                graphics.fill(area);

    }

}

