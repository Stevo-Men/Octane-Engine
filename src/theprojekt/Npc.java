package theprojekt;

import doctrina.Canvas;
import doctrina.CollidableRepository;
import doctrina.MovableEntity;
import doctrina.StaticEntity;

import java.awt.*;

public class Npc  {

    private int x;
    private int y;
    private int width;
    private int height;
    private int speed;
    private boolean path1 = true;
    private boolean path2 = false;
    private boolean path3 = false;
    private boolean path4 = false;
    private CollidableRepository collidableRepository;
    private MovableEntity npcEntity;

    public Npc(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 1;
        width = 16;
        height = 32;

    }

    public void update() {
       trajectory();

    }

    public void isKilled(Player player) {

    }

    private void trajectory() {
        if (path1) {
            y += speed;
            if (y >= 500) {
                path1 = false;
                path2 = true;
            }
        } else if (path2) {
            x -= speed;
            if (x <= 100) {
                path2 = false;
                path3 = true;
            }
        } else if (path3) {
            y -= speed;
            if (y <= 200) {
                path3 = false;
                path4 = true;
            }
        } else if (path4) {
            x += speed;
            if (x >= 400) {
                path4 = false;
                path1 = true;
            }
        }
    }

    //make an interface of movable entity and make the npc implement it

    public void draw(Canvas canvas, int cameraX, int cameraY) {
        canvas.drawRectangle(x - cameraX, y - cameraY, width, height, Color.YELLOW);
        canvas.drawString(" " + x + " " + y,x - cameraX,y - cameraY,Color.RED);
    }



}
