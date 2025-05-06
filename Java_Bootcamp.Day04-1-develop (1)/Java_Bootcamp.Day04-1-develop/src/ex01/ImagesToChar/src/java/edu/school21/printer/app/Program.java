package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Program {
    
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: java Program  <char_for_white> <char_for_black>");
            return;
        }

        char whiteChar = args[0].charAt(0);
        char blackChar = args[1].charAt(0);

        BufferedImage image = null;
        try (InputStream is = Program.class.getResourceAsStream("/it.bmp")) {
            if (is == null) {
                System.err.println("Image not found!");
                return;
            }
            image = ImageIO.read(is);
        } catch (IOException e) {
            System.err.println("Error reading the image: " + e.getMessage());
            return;
        }

        ImageConverter converter = new ImageConverter();
        converter.printImage(image, whiteChar, blackChar);
    }
}