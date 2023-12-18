package doctrina;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GradientEllipse extends JFrame {

    public GradientEllipse() {
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        int centerX = 300;
        int centerY = 400;
        int maxRadius = 100;

        // Draw ellipses with increasing radii and decreasing opacity
        for (int radius = 1; radius <= maxRadius; radius++) {
            float opacity = 1.0f - (float) radius / maxRadius;
            Color color = new Color(255, 0, 0, (int) (255 * opacity));
            g2d.setColor(color);

            // Calculate ellipse bounds
            int ellipseX = centerX - radius;
            int ellipseY = centerY - radius;
            int ellipseWidth = 2 * radius;
            int ellipseHeight = 2 * radius;

            // Draw the ellipse
            g2d.fill(new Ellipse2D.Float(ellipseX, ellipseY, ellipseWidth, ellipseHeight));
        }
    }


}
