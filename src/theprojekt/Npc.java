package theprojekt;

import doctrina.Canvas;
import doctrina.CollidableRepository;
import doctrina.MovableEntity;

import java.awt.*;

public class Npc extends MovableEntity{



    private int speed;
    private boolean path1 = true;
    private boolean path2 = false;
    private boolean path3 = false;
    private boolean path4 = false;
    private int health = 50;
    private Knife knife;
    private Map map;
    private Camera camera;


    public Npc(int x, int y) {
        this.x = x;
        this.y = y;
        speed = 1;
        setDimension(32, 32);
        camera = new Camera();
        CollidableRepository.getInstance().registerEntity(this);
    }

    public void update() {
        super.update();


        trajectory();

    }



    public void isTouched(Knife knife) {
        this.health -= knife.damage;
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


    public int getHealth() {
        return health;
    }



    public void draw(Canvas canvas, int cameraX, int cameraY) {
        canvas.drawRectangle(getBoundsNPC().x - cameraX  , getBoundsNPC().y - cameraY, width, height, Color.YELLOW);
        canvas.drawString(" " + getBoundsNPC().x + " " + getBoundsNPC().y ,x - cameraX,y - cameraY,Color.RED);
        drawHealthEnemy(canvas,getBoundsNPC().x,getBoundsNPC().y);

    }


    private void drawHealthEnemy(Canvas canvas, int cameraX, int cameraY) {
        canvas.drawRectangle(x - cameraX, y - cameraY,50, 10, Color.RED);
        canvas.drawRectangle(x - cameraX, y - cameraY,50, 10, Color.GREEN);
    }
    @Override
    public void draw(Canvas canvas) {

    }




}
