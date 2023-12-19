package theprojekt;

import doctrina.*;
import doctrina.Canvas;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

public class HUD extends StaticEntity {
    private Screen screen;
    private int healthBarX;
    private int healthBarY;
    private int playerHealth;
    private static final String knifePath = "images/knife.png";
    private static final String hudTexturePath = "images/hudTexture.png";
    private static final String hudDetectedPath = "images/hudDetected2.png";
    private Image knifeImage;
    private Image hudTexturePanel;
    private Image hudDetectedTexture;
    private RessourceLoader ressourceLoader;
    private Font customFont;
    private int gameOverY = 100;
    private Player player;
    private Camera camera;
    private boolean detected = false;
    private int knifeMunition;

    public HUD() {
        screen = new Screen();
        camera = new Camera();
        player = new Player(new MovementController());
        ressourceLoader = new RessourceLoader();
        load();

    }

    private Image loadImage(String imagePath) {
        try {
            return ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream(imagePath));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }

    public void load() {
        hudTexturePanel = loadImage(hudTexturePath);
        knifeImage = loadImage(knifePath);
       hudDetectedTexture = loadImage(hudDetectedPath);

    }

    void update(Player player) {
        playerHealth = player.playerHealth;
        knifeMunition = player.getKnifeMunition();
        detected = player.isDetected();
    }




    public void drawHealthBar(Canvas canvas) {
        healthBarX = screen.getWidth()+10;
        healthBarY = screen.getHeight()+10;
        canvas.drawRectangle(healthBarX-2, healthBarY-2, 104, 24, Color.WHITE);
        canvas.drawRectangle(healthBarX, healthBarY, 100, 20, Color.RED);
        canvas.drawRectangle(healthBarX, healthBarY, playerHealth, 20, Color.GREEN);
    }

    public  void drawKnifeMunition(Canvas canvas) {
        int knifeMunitionX = screen.getWidth()+10;
        int knifeMunitionY = screen.getHeight()+50;
        canvas.drawInfo("Knife : " + knifeMunition , knifeMunitionX, knifeMunitionY, Color.WHITE, customFont, 10);
        canvas.drawImage(knifeImage , knifeMunitionX, knifeMunitionY + 10);
    }



    public void hudTexture(Canvas canvas, MovableEntity player) {
        update((Player) player);
        int playerX = player.getX();
        int playerY = player.getY();

        Graphics2D g2d = canvas.getGraphics();
        float opacity = 0.3f;
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2d.setComposite(alphaComposite);

        int ellipseRadiusX = 60;
        int ellipseRadiusY = 60;

        Ellipse2D ellipse = new Ellipse2D.Float(playerX - 44, playerY - 44, 2 * ellipseRadiusX, 2 * ellipseRadiusY);
        Rectangle rect = new Rectangle(-30, -15, 972, 610);
        Area area = new Area(rect);
        area.subtract(new Area(ellipse));
        g2d.setClip(area);
        if (detected) {
            g2d.drawImage(hudDetectedTexture, -30, -15, 972, 610, null);
        } else {
            g2d.drawImage(hudTexturePanel, -30, -15, 972, 610, null);
        }
        g2d.setClip(ellipse);
        g2d.dispose();
    }


    public void drawGameOver(Canvas canvas) {
        canvas.drawInfo("G A M E  O V E R", 130, gameOverY, Color.RED, customFont, 60);
        gameOverY += 100;
    }

    @Override
    public void draw(Canvas canvas) {
        drawHealthBar(canvas);
        drawKnifeMunition(canvas);
    }
}
