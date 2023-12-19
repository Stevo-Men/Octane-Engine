package theprojekt;

import doctrina.*;
import doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AnimatedEntity extends MovableEntity{

    private BufferedImage spriteSheet;
    private int currentAnimationFrame = 1;
    private final int ANIMATION_SPEED = 8;
    private int nextFrame = ANIMATION_SPEED;

    protected Map<Direction, Image[]> directionFramesMap = new HashMap<>();

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public AnimatedEntity(String spritePath,  int width, int height) {
        this.width = width;
        this.height = height;
        loadSpriteSheet(spritePath);
    }

    protected void loadSpriteSheet(String spritePath) {
        try {
            spriteSheet = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(spritePath));
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

    protected void handleAnimation(MovableEntity entity) {
        if (entity.hasMoved()) {
            --nextFrame;
            if (nextFrame == 0) {
                ++currentAnimationFrame;
                if (currentAnimationFrame >= directionFramesMap.get(entity.getDirection()).length) {
                    currentAnimationFrame = 0;
                }
                nextFrame = ANIMATION_SPEED;
            }
        } else {
            currentAnimationFrame = 1;
        }

    }

    protected void drawCharacterImage(Canvas canvas, int translatedX, int translatedY, MovableEntity entity) {
        Direction direction = entity.getDirection();
        Image[] frames = directionFramesMap.get(direction);

        if (frames != null) {
            canvas.drawImage(frames[currentAnimationFrame], entity.getX() - translatedX, entity.getY() - translatedY);
        }
    }

    public void update(MovableEntity entity) {
        handleAnimation(entity);
        loadAnimationFrames();
    }


    @Override
    public void draw(Canvas canvas) {

    }
}
