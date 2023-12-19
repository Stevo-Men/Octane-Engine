package theprojekt;
import doctrina.*;
import doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Chest extends StaticEntity{

    boolean isAlive = true;
    private int ammo;
    private int health;
    private static final String CHEST_PATH = "images/chest.png";
    private BufferedImage chestImage;
    private static final String OPEN_CHEST_PATH = "images/open_chest.png";
    private BufferedImage openChestImage;

    public Chest(int x, int y, int ammo, int health) {
        this.x = x;
        this.y = y;
        width = 32;
        height = 32;
        this.ammo = ammo;
        this.health = health;
        load();
    }

    public void load() {
       chestImage = loadImage(CHEST_PATH, chestImage);
       openChestImage = loadImage(OPEN_CHEST_PATH, openChestImage);
    }

    public void open(Player player) {
            player.knifeMunition += ammo;
            player.playerHealth += health;
            isAlive = false;
    }


    @Override
    public void draw(Canvas canvas) {
        if (isAlive) {
            canvas.drawImage(chestImage, x, y);
        } else {
            canvas.drawImage(openChestImage, x, y);
        }
    }

    private BufferedImage loadImage(String path, BufferedImage imageName) {
        try {
            imageName = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(path));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return imageName;
    }
}
