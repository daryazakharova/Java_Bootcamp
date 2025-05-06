package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.Ansi;

public class ImageConverter {

    public static void convert(String black, String white) throws IOException {
        Attribute insteadBlack = toAttribute(black);
        Attribute insteadWhite = toAttribute(white);

        File file = new File("target/it.bmp");
        BufferedImage image = ImageIO.read(file);
        int width = image.getWidth();
        int height = image.getHeight();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int color = image.getRGB(x, y);
                if (color == Color.BLACK.getRGB()) {
                    System.out.print(Ansi.colorize(" ", insteadBlack));
                } else {
                    System.out.print(Ansi.colorize(" ", insteadWhite));
                }
            }
            System.out.println();
        }
    }

    private static Attribute toAttribute(String attribute) {
    switch (attribute.toLowerCase()) {
        case "black":
            return Attribute.BLACK_BACK();
        case "green":
            return Attribute.GREEN_BACK();
        case "blue":
            return Attribute.BLUE_BACK();
        case "red":
            return Attribute.RED_BACK();
        case "yellow":
            return Attribute.YELLOW_BACK();
        case "magenta":
            return Attribute.MAGENTA_BACK();
        case "cyan":
            return Attribute.CYAN_BACK();
        default:
            return Attribute.WHITE_BACK();
        }
    }
}
