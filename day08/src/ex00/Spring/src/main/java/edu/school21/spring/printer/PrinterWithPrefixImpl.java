package edu.school21.spring.printer;

import edu.school21.spring.renderer.Renderer;
import edu.school21.spring.renderer.RendererErrImpl;

public class PrinterWithPrefixImpl implements Printer {
    private Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void print(String message) {
        renderer.render(prefix + message);
    }
}
