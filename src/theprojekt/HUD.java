package theprojekt;

import doctrina.Canvas;
import doctrina.Screen;
import doctrina.StaticEntity;

import java.awt.*;

public class HUD extends StaticEntity {
    private Screen screen;
    private int healthBarX;
    private int healthBarY;


    public HUD() {
        screen = new Screen();

    }



    public void drawHealthBar(Canvas canvas, Player player) {
        healthBarX = screen.getWidth()+10;
        healthBarY = screen.getHeight()+10;
        canvas.drawRectangle(healthBarX, healthBarY, 100, 20, Color.RED);
        canvas.drawRectangle(healthBarX, healthBarY, player.playerHealth, 20, Color.GREEN);

    }


    @Override
    public void draw(Canvas canvas) {
        drawHealthBar(canvas,new Player(null));
    }
}
