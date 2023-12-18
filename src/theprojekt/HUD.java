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
    private static final String alertPath = "src/theprojekt/Assets/Alert.png";
    private static final String knifePath = "images/knife.png";
    private static final String hudTexturePath = "images/hudTexture.png";
    private Image knifeImage;
    private Image hudTexturePanel;
    private RessourceLoader ressourceLoader;
    private Font customFont;
    private Graphics graphics;
    private Player player;
    private Camera camera;
    private GradientEllipse gradientEllipse;
    private int knifeMunition;

    public HUD() {
        screen = new Screen();
        camera = new Camera();
        player = new Player(new MovementController());
        ressourceLoader = new RessourceLoader();
        gradientEllipse = new GradientEllipse();
        load();

    }

    public Image loadImage() {
        try {
            hudTexturePanel = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream(hudTexturePath));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return hudTexturePanel;
    }

    public Image loadImageKnife() {
        try {
            knifeImage = ImageIO.read(
                    this.getClass().getClassLoader().getResourceAsStream(knifePath));
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return knifeImage;
    }

    void update(Player player) {
        playerHealth = player.playerHealth;
        knifeMunition = player.getKnifeMunition();
    }

    public void load() {
       //ressourceLoader.loadImage(mainPanel , techPanel);
        loadImage();
        loadImageKnife();
    }


    public void drawHealthBar(Canvas canvas, Player player) {
        healthBarX = screen.getWidth()+10;
        healthBarY = screen.getHeight()+10;
        canvas.drawRectangle(healthBarX-2, healthBarY-2, 104, 24, Color.WHITE);
        canvas.drawRectangle(healthBarX, healthBarY, 100, 20, Color.RED);
        canvas.drawRectangle(healthBarX, healthBarY, playerHealth, 20, Color.GREEN);
    }

    public  void drawKnifeMunition(Canvas canvas, Player player) {

        int knifeMunitionX = screen.getWidth()+10;
        int knifeMunitionY = screen.getHeight()+50;
        canvas.drawInfo("Knife : " + knifeMunition , knifeMunitionX, knifeMunitionY, Color.WHITE, customFont, 10);
        canvas.drawImage(knifeImage , knifeMunitionX, knifeMunitionY + 10);
    }

    public void drawAlert(Canvas canvas) {
        canvas.drawString("You've been detected", 400, 400, Color.RED);
       // canvas.drawImage(alert, 1000, 1000);
    }


    public void hudTexture(Canvas canvas, MovableEntity player) {
        int playerX = player.getX();
        int playerY = player.getY();

        Graphics2D g2d = canvas.getGraphics();

        float opacity = 0.3f;
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity);
        g2d.setComposite(alphaComposite);

        // Create a circular shape centered around the player
        // Create an ellipse centered around the player
        int ellipseRadiusX = 40; // Adjust the X-axis radius as needed
        int ellipseRadiusY = 40; // Adjust the Y-axis radius as needed
        Ellipse2D ellipse = new Ellipse2D.Float(playerX - 22, playerY - 22, 2 * ellipseRadiusX, 2 * ellipseRadiusY);

        // Create a rectangular shape representing the HUD area
        Rectangle rect = new Rectangle(-30, -15, 972, 610);

        // Create an Area representing the difference between the rectangle and the circle
        Area area = new Area(rect);


        area.subtract(new Area(ellipse));


        g2d.setClip(area);

        // Fill the area with the desired HUD texture (background)
        g2d.drawImage(hudTexturePanel, -30, -15, 972, 610, null);


        g2d.setClip(ellipse);



        // Dispose of the graphics context to release resources
        g2d.dispose();
    }




    public void drawPauseMenu(Canvas canvas, boolean paused) {
        if (paused) {
        canvas.drawInfo(" P A U S E ", 160, 300, Color.WHITE, customFont, 60);
    }
    }

    @Override
    public void draw(Canvas canvas) {
        drawHealthBar(canvas,player);
        drawKnifeMunition(canvas,player);
    }
}
