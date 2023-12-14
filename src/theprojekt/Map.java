package theprojekt;

import doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Map {

    private static final String MAP_PATH = "images/sewer_map.png";
    private Image background;
    public int x; // X-coordinate of the map
    public int y; // Y-coordinate of the map
    private int drawX,drawY;

    public void load() {
        try {
            background = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream(MAP_PATH));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
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



    public void draw(Canvas canvas, int cameraX, int cameraY) {
        // Adjust the drawing position based on the camera's position
        int drawX = x - cameraX;
        int drawY = y - cameraY;

        // Draw the background at the adjusted position
        canvas.drawImage(background, drawX, drawY);
    }


}
