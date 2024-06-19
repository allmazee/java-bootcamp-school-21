package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;

public class ImageConverter {
    private final String black;
    private final String white;
    private final String filePath;

    public ImageConverter(String black, String white) {
        this.filePath = "/resources/it.bmp";
        this.black = black;
        this.white = white;
    }

    public void print() {
        try {
            BufferedImage image = ImageIO.read(ImageConverter.class.getResource(filePath));
            Color blackColor = Color.BLACK;
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    int color = image.getRGB(j, i);
                    if (color == blackColor.getRGB()) {
                        System.out.print(Ansi.colorize(" ", toColor(black)));
                    } else {
                        System.out.print(Ansi.colorize(" ", toColor(white)));
                    }
                }
                System.out.println();
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private Attribute toColor(String color) {
         switch (color) {
             case "BLACK":
                 return Attribute.BLACK_BACK();
             case "BLUE":
                 return Attribute.BLUE_BACK();
             case "GREEN":
                 return Attribute.GREEN_BACK();
             case "CYAN":
                 return Attribute.CYAN_BACK();
             case "MAGENTA":
                 return Attribute.MAGENTA_BACK();
             case "RED":
                 return Attribute.RED_BACK();
             case "WHITE":
                 return Attribute.WHITE_BACK();
             case "YELLOW":
                 return Attribute.YELLOW_BACK();
             default:
                 System.out.println("Available colors: BLACK, BLUE, GREEN," +
                         " CYAN, MAGENTA, RED, WHITE, YELLOW");
                 System.exit(-1);
         }
         return null;
    }

}
