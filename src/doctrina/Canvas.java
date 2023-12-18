package doctrina;

import java.awt.*;

public class Canvas {

    private final Graphics2D graphics;
    private RessourceLoader ressourceLoader;

    public Canvas(Graphics2D graphics) {
        this.graphics = graphics;
        ressourceLoader = new RessourceLoader();
    }

    public void drawRectangle(int x, int y, int width, int height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(x, y, width, height);
    }

    public void drawRectangle(StaticEntity entity, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRect(entity.x, entity.y, entity.width, entity.height);
    }

    public void drawCircle(int x, int y, int radius, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillOval(x, y, radius * 2, radius * 2);
    }

    public void drawString(String text, int x, int y, Paint paint) {
        graphics.setPaint(paint);
        graphics.drawString(text, x, y);
    }

    public void drawInfo(String text, int x, int y, Paint paint, Font customFont, int fontSize) {
        customFont = ressourceLoader.loadFont(fontSize);

        graphics.setFont(customFont);

        // Set paint color
        graphics.setPaint(paint);

        // Draw the string
        graphics.drawString(text, x, y);
    }


    public void drawHealthNPC(int x, int y, int width, int height, Paint paint) {
        graphics.setPaint(paint);
        graphics.fillRoundRect(x, y, width, height, 10, 10);
    }

    public void drawDetectionRect(Rectangle rectangle, Paint paint) {
        graphics.setPaint(paint);
        graphics.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    public void drawImage(Image image, int x, int y) {
        graphics.drawImage(image, x, y, null);

    }

    public Graphics2D getGraphics() {
        return graphics;
    }
}
