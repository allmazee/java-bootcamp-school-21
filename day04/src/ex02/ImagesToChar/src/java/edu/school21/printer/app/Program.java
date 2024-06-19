package edu.school21.printer.app;

import com.beust.jcommander.ParameterException;
import edu.school21.printer.logic.ImageConverter;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class Program {
    @Parameter(names = {"-b", "--black"})
    private String black;

    @Parameter(names = {"-w", "--white"})
    private String white;

    public static void main(String[] args) {
        Program program = new Program();
        try {
            JCommander.newBuilder().addObject(program).build().parse(args);
            ImageConverter converter = new ImageConverter(program.black, program.white);
            converter.print();
        } catch (ParameterException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

    }
}
