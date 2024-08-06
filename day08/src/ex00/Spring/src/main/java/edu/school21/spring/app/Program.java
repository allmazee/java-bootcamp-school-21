package edu.school21.spring.app;

import edu.school21.spring.preprocessor.*;
import edu.school21.spring.printer.*;
import edu.school21.spring.renderer.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Program {
    public static void main(String[] args) {
        PreProcessor preProcessor = new PreProcessorToLowerImpl();
        Renderer renderer = new RendererStandardImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Without spring: ");
        printer.print("HELLO!");

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "applicationContext.xml");
        PrinterWithPrefixImpl printerWithPrefix = applicationContext.getBean(
                "printerWithPrefixImpl", PrinterWithPrefixImpl.class);
        printerWithPrefix.setPrefix("Using Spring: ");
        printerWithPrefix.print("Hello!!");
        PrinterWithDateTimeImpl printerWithDateTime = applicationContext.getBean(
                "printerWithDateTimeImpl", PrinterWithDateTimeImpl.class);
        printerWithDateTime.print("HOWDY PARTNER");
    }
}
