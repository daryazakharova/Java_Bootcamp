package edu.school21.printer.logic;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageConverter {
    public void printImage(BufferedImage image, char whiteChar, char blackChar) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Color c = new Color(image.getRGB(x, y));
                if (c.equals(Color.BLACK)) {
                    System.out.print(blackChar);
                } else {
                    System.out.print(whiteChar);
                }
            }
            System.out.println();
        }
    }
}