package edu.school21.printer.app;

import edu.school21.printer.logic.ImageConverter;

public class Program {
    public static void main(String[] args) {
        ImageConverter converter = new ImageConverter(args);
        converter.print();
    }
}
