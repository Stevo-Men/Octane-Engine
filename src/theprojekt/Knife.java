package theprojekt;

import doctrina.Canvas;
import doctrina.CollidableRepository;
import doctrina.Direction;
import doctrina.MovableEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.IOException;

public class Knife extends MovableEntity {

    private Direction playerDirection;
    private static final String SPRITE_PATH = "images/bulletmini.png";
    private Image image;
    protected int damage = 25;


    public Knife(Player player) {
        setSpeed(7);
        playerDirection = player.getDirection();
        load();
        initialize(player);
        CollidableRepository.getInstance().registerEntity(this);
    }



    @Override
    public void update() {
        if (playerDirection == Direction.RIGHT) {
            x += getSpeed();
        } else if (playerDirection == Direction.LEFT) {
            x -= getSpeed();
        } else if (playerDirection == Direction.DOWN) {
            y += getSpeed();
        } else if (playerDirection == Direction.UP) {
            y -= getSpeed();
        }

     /*   if (x >= 820 || x < 0) {
            x = -20;
        }
        if (y >= 620 || y < 0) {
            y = -20;
        }*/
    }


    @Override
    public void draw(Canvas canvas) {
        Graphics2D g2d = canvas.getGraphics();

        AffineTransform originalTransform = g2d.getTransform();

        double rotationAngle = 0.0;
        switch (playerDirection) {
            case RIGHT:
                rotationAngle =  Math.PI / 2;
                break;
            case LEFT:
                rotationAngle = -Math.PI / 2;
                break;
            case DOWN:
                rotationAngle = Math.PI;
                break;
            case UP:
                rotationAngle = 0.0;
                break;
        }
        g2d.rotate(rotationAngle, x + getWidth() / 2, y + getHeight() / 2);
        g2d.drawImage(image, x, y, null);
        g2d.setTransform(originalTransform);
    }


    private void initialize(Player player) {
        if (playerDirection == Direction.RIGHT) {
            teleport(player.getX() + player.getWidth() + 1,
                    player.getY() + 15 - 2);
            setDimension(4, 2);
        } else if (playerDirection == Direction.LEFT) {
            teleport(player.getX() - 9, player.getY() + 15 - 2);
            setDimension(4, 2);
        } else if (playerDirection == Direction.DOWN) {
            teleport(player.getX() + 15 - 2,
                    (player.getY() + player.getHeight() + 1));
            setDimension(4, 2);
        } else if (playerDirection == Direction.UP) {
            teleport(player.getX() + 15 - 2, player.getY() - 9);
            setDimension(4, 2);
        }
    }

    private void load() {
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream(SPRITE_PATH));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}


