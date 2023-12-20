package theprojekt;

import doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Map {

    private static final String MAP_PATH = "images/sewer_map.png";
    private Image background;
    public int mapX;
    public int mapY;
    public int mapWidth;
    public int mapHeight;
    private int drawX,drawY;
    private Camera camera;



    public void load() {
        try {
            background = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream(MAP_PATH));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }



    public int getX() {
        return mapX;
    }

    public int getY() {
        return mapY;
    }



    public void draw(Canvas canvas) {
        canvas.drawImage(background, drawX, drawY);
    }


}
