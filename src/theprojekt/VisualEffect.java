package theprojekt;

import doctrina.Canvas;
import doctrina.Direction;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VisualEffect {
    private Camera camera;
    private Player player;
    private Canvas canvas;

    private int lightX;
    private int lightY;
    private int lightWidth = 25;
    private int lightHeight = 70;
    private final float darkTintOpacity = 0.9f;
    protected Map<Direction, Direction.TriConsumer<Integer, Integer, Integer>> directionCalculations = new HashMap<>();


    public VisualEffect(Player player) {
        camera = new Camera();
        this.player = player;
    }



    public void drawPlayerLight(Canvas canvas) {
        Direction direction = player.getDirection();


        directionCalculations.put(Direction.RIGHT, (x, y, width) -> {
            lightX = x + player.getWidth() - 12;
            lightY = y + 15 - 2;
            addDarkTint(canvas, darkTintOpacity,false);

        });

        directionCalculations.put(Direction.LEFT, (x, y, width) -> {
            lightX = x - 56;
            lightY = y + 15 - 2;
            addDarkTint(canvas, darkTintOpacity,false);

        });

        directionCalculations.put(Direction.DOWN, (x, y, width) -> {
            lightX = x + 6;
            lightY = y + player.getHeight() + 1;
            addDarkTint(canvas, darkTintOpacity,true);

        });

        directionCalculations.put(Direction.UP, (x, y, width) -> {
            lightX = x + 4;
            lightY = y - 58;
            addDarkTint(canvas, darkTintOpacity,true);

        });

        directionCalculations.getOrDefault(direction, (x, y, width) -> {}).accept(player.getX(), player.getY(), player.getWidth());
    }

    private void addDarkTint(Canvas canvas, float darkTintOpacity, boolean vertical){

        Graphics2D graphics = canvas.getGraphics();
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, darkTintOpacity));

        int drawX = camera.translateX(camera.getX());
        int drawY = camera.translateY(camera.getY());
        int canvasWidth = 1000;
        int canvasHeight = 600;



        Rectangle rect = new Rectangle(drawX, drawY, canvasWidth, canvasHeight);
        Ellipse2D ovalVertical = new Ellipse2D.Float(lightX, lightY, lightWidth, lightHeight);
        Ellipse2D ovalHorizontal = new Ellipse2D.Float(lightX, lightY, lightHeight, lightWidth);
        Ellipse2D circle = new Ellipse2D.Float(player.getX() - 32, player.getY() - 32, 100, 100);

        Area area = new Area(rect);



        if (vertical) {
            area.subtract(new Area(ovalVertical));
            area.subtract(new Area(circle));

        } else {
            area.subtract(new Area(ovalHorizontal));
            area.subtract(new Area(circle));
        }

        graphics.setClip(area);
        graphics.setColor(Color.BLACK);
        graphics.fill(area);
    }



}
