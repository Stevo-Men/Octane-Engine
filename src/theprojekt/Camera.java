package theprojekt;

import doctrina.Canvas;
import doctrina.Screen;

import java.awt.*;

public class Camera {
    private int x;
    private int y;
    private GamePad gamePad;
    private Rectangle rectangle;
    private Screen screen;

    public Camera() {
        this.x = 0;
        this.y = 0;
        gamePad = new GamePad();
        screen = new Screen();
    }

    public void update() {
        if (gamePad.isDownPressed()) {
            move(0, -1);
        }
        if (gamePad.isUpPressed()) {
            move(0, 1);
        }
        if (gamePad.isRightPressed()) {
            move(-1, 0);
        }
        if (gamePad.isLeftPressed()) {
            move(1, 0);
        }
    }



    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    // Set the camera to a specific position
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Apply the camera translation to the given coordinates
    public int translateX(int worldX) {
        return worldX - x;
    }

    public int translateY(int worldY) {
        return worldY - y;
    }

    public void drawDarkTint(Canvas canvas, float darkTintOpacity) {
        // Apply a dark tint by setting the composite
        Graphics2D graphics = canvas.getGraphics();
        Composite originalComposite = graphics.getComposite();
        AlphaComposite darkTintComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, darkTintOpacity);
        graphics.setComposite(darkTintComposite);

        // Draw a black rectangle with the specified opacity
        graphics.setColor(Color.BLACK);
        graphics.fillRect(translateX(x), translateY(y), 1000, 600);

        // Restore the original composite
        graphics.setComposite(originalComposite);
    }

}

