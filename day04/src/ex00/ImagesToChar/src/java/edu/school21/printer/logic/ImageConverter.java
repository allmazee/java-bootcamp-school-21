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
    }

    public void print() {
        read();
        try {
            BufferedImage image = ImageIO.read(new File(filePath));
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
                    "Program --black=[SYMBOL] --white=[SYMBOL] --path=[PATH]");
            System.exit(-1);
        }
        String[] blackArg = args[0].split("--black=");
        String[] whiteArg = args[1].split("--white=");
        String[] pathArg = args[2].split("--path=");
        black = blackArg[1].charAt(0);
        white = whiteArg[1].charAt(0);
        filePath = pathArg[1];
    }

    private boolean isValid() {
        if (args.length != 3) {
            return false;
        }
        if (!(args[0].startsWith("--black=") && args[1].startsWith("--white=")
                && args[2].startsWith("--path="))) {
            return false;
        }
        String[] blackArg = args[0].split("--black=");
        String[] whiteArg = args[1].split("--white=");
        String[] pathArg = args[2].split("--path=");
        return blackArg.length == 2 && whiteArg.length == 2 && pathArg.length == 2;
    }
}
