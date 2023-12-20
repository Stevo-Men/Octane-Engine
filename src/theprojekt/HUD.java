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
        canvas.drawRectangle(healthBarX-4, healthBarY-2, playerHealth*2+4, 24, Color.WHITE);
        canvas.drawRectangle(healthBarX, healthBarY, playerHealth*2+4, 20, Color.RED);
        canvas.drawRectangle(healthBarX, healthBarY, playerHealth*2, 20, Color.GREEN);
    }

    public  void drawKnifeMunition(Canvas canvas) {
        int knifeMunitionX = screen.getWidth()+10;
        int knifeMunitionY = screen.getHeight()+50;
        canvas.drawInfo("AMMO : " + knifeMunition , knifeMunitionX, knifeMunitionY, Color.WHITE, customFont, 16);
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
        canvas.drawInfo("G A M E  O V E R", 20, 100, new Color(189, 0, 0, 255), customFont, 50);
        canvas.drawInfo("G A M E  O V E R", 20, 200, new Color(189, 0, 0, 211), customFont, 50);
        canvas.drawInfo("G A M E  O V E R", 20, 300, new Color(189, 0, 0, 173), customFont, 50);
        canvas.drawInfo("G A M E  O V E R", 20, 400, new Color(121, 0, 0, 147), customFont, 50);
        canvas.drawInfo("G A M E  O V E R", 20, 500, new Color(52, 0, 0, 142), customFont, 50);
    }

    public void drawWin(Canvas canvas) {
        canvas.drawInfo("L E V E L  C L E A R E D", 20, 100, Color.ORANGE, customFont, 45);
        canvas.drawInfo("L E V E L  C L E A R E D", 20, 200, Color.ORANGE, customFont, 45);
        canvas.drawInfo("L E V E L  C L E A R E D", 20, 300, Color.ORANGE, customFont, 45);
        canvas.drawInfo("L E V E L  C L E A R E D", 20, 400, Color.ORANGE, customFont, 45);
        canvas.drawInfo("L E V E L  C L E A R E D", 20, 500, Color.ORANGE, customFont, 45);
    }

    @Override
    public void draw(Canvas canvas) {
        drawHealthBar(canvas);
        drawKnifeMunition(canvas);
    }
}
