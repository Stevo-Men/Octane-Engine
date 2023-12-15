package theprojekt;

import doctrina.Canvas;
import doctrina.Screen;
import doctrina.StaticEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class HUD extends StaticEntity {
    private Screen screen;
    private int healthBarX;
    private int healthBarY;
    private int playerHealth;
    private static final String alertPath = "src/theprojekt/Assets/Alert.png";
    private Image alert;



    public HUD() {
        screen = new Screen();

    }

    void update(Player player) {
        playerHealth = player.playerHealth;

    }

    public void load() {
        try {
            alert = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream(alertPath));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }


    public void drawHealthBar(Canvas canvas, Player player) {
        healthBarX = screen.getWidth()+10;
        healthBarY = screen.getHeight()+10;
        canvas.drawRectangle(healthBarX, healthBarY, 100, 20, Color.RED);
        canvas.drawRectangle(healthBarX, healthBarY, playerHealth, 20, Color.GREEN);
    }

    public void drawAlert(Canvas canvas) {
        canvas.drawString("You've been detected", 400, 400, Color.RED);
        canvas.drawImage(alert, 1000, 1000);
    }


    @Override
    public void draw(Canvas canvas) {
        drawHealthBar(canvas,new Player(null));

    }
}
