package edu.school21.printer.logic;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageConverter {
    private final String[] args;
    private char black;
    private char white;
    private String filePath;

    public ImageConverter(String[] args) {
        this.args = args;
        this.filePath = "/resources/it.bmp";
    }

    public void print() {
        try {
            read();
            BufferedImage image = ImageIO.read(ImageConverter.class.getResource(filePath));
            //BufferedImage image = ImageIO.read(new File(filePath));
            Color blackColor = Color.BLACK;
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    int color = image.getRGB(j, i);
                    if (color == blackColor.getRGB()) {
                        System.out.print(black);
                    } else {
                        System.out.print(white);
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void read() {
        if (!isValid()) {
            System.out.println("Usage: java -classpath target edu.school21.printer.app." +
                    "Program --black=[SYMBOL] --white=[SYMBOL]");
            System.exit(-1);
        }
        String[] blackArg = args[0].split("--black=");
        String[] whiteArg = args[1].split("--white=");
        black = blackArg[1].charAt(0);
        white = whiteArg[1].charAt(0);
    }

    private boolean isValid() {
        if (args.length != 2) {
            return false;
        }
        if (!(args[0].startsWith("--black=") && args[1].startsWith("--white="))) {
            return false;
        }
        String[] blackArg = args[0].split("--black=");
        String[] whiteArg = args[1].split("--white=");
        return blackArg.length == 2 && whiteArg.length == 2;
    }
}
