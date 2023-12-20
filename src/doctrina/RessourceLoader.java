package doctrina;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class RessourceLoader {

    private static final String SPRITE_PATH = "/font/AKONY.ttf";

    public Font loadFont(float fontSize) {
        try {
            InputStream inputStream = getClass().getResourceAsStream(SPRITE_PATH);

            Font customFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);


            return customFont.deriveFont(fontSize);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();

        }
        return null;
    }





}
