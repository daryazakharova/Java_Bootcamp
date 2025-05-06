package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Program {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java -cp target/ ImagesToChar <path_to_image> <char_for_white> <char_for_black>");
            return;
        }

        String imagePath = args[0];
        char whiteChar = args[1].charAt(0);
        char blackChar = args[2].charAt(0);

        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            ImageConverter converter = new ImageConverter();
            converter.printImage(image, whiteChar, blackChar);
        } catch (IOException e) {
            System.err.println("Error reading the image: " + e.getMessage());
        }
    }
}