package theprojekt;

import doctrina.Canvas;
import doctrina.Screen;

import java.awt.*;

public class Camera {
    private int x;
    private int y;



    public Camera() {
        this.x = 0;
        this.y = 0;


    }



    public void update(Player player) {
        if (player.getX() > 400) {
            x = player.getX() - 400;
        }
        if (player.getY() > 300) {
            y = player.getY() - 300;
        }
    }

    public void draw(Canvas canvas) {

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


    // Apply the camera translation to the given coordinates
    public int translateX(int worldX) {
        return worldX - x;
    }

    public int translateY(int worldY) {
        return worldY - y;
    }



}

