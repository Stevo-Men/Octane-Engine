package theprojekt;

import doctrina.Canvas;
import doctrina.ControllableEntity;
import doctrina.Direction;
import doctrina.MovementController;
import tank.Tank;

import javax.imageio.ImageIO;
import java.awt.*;
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

    int lightX;
    int lightY;
    int lightWidth;
    int lightHeight;
    Map<Direction, Direction.TriConsumer<Integer, Integer, Integer>> directionCalculations = new HashMap<>();

    public Player(MovementController controller) {
        super(controller);
        setDimension(32, 32);
        setSpeed(3);
        load();

    }

    @Override
    public void update() {
        super.update();
        moveWithController();
        handleAnimation();
    }

    @Override
    public void draw(Canvas canvas) {
        drawPlayerImage(canvas, 0, 0);
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
        int commonWidth = 20;
        int commonHeight = 64;


        directionCalculations.put(Direction.RIGHT, (x, y, width) -> {
            lightX = x + this.getWidth() - 16;
            lightY = y + offsetY;
            lightWidth = commonHeight;
            lightHeight = commonWidth;
        });
        directionCalculations.put(Direction.LEFT, (x, y, width) -> {
            lightX = x - 48;
            lightY = y + offsetY;
            lightWidth = commonHeight;
            lightHeight = commonWidth;
        });
        directionCalculations.put(Direction.DOWN, (x, y, width) -> {
            lightX = x + offsetX;
            lightY = y + this.getHeight() + 1;
            lightWidth = commonWidth;
            lightHeight = commonHeight;
        });
        directionCalculations.put(Direction.UP, (x, y, width) -> {
            lightX = x + offsetX;
            lightY = y - 50;
            lightWidth = commonWidth;
            lightHeight = commonHeight;
        });

        directionCalculations.getOrDefault(direction, (x, y, width) -> {}).accept(this.getX(), this.getY(), this.getWidth());

        Color lightColor = new Color(255, 255, 255, 147);
        canvas.drawRectangle(lightX, lightY, lightWidth, lightHeight, lightColor);
    }

}

