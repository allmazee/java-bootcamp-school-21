package edu.school21.processors;

import com.google.auto.service.AutoService;
import edu.school21.annotations.HtmlForm;
import edu.school21.annotations.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@AutoService(Processor.class)
@SupportedAnnotationTypes("edu.school21.annotations.HtmlForm")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HtmlProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations,
                           RoundEnvironment roundEnv) {
        for (Element element
                : roundEnv.getElementsAnnotatedWith(HtmlForm.class)) {
            HtmlForm htmlForm = element.getAnnotation(HtmlForm.class);
            try {
                generateHtmlForm(element, htmlForm);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private void generateHtmlForm(Element element,
                                  HtmlForm htmlForm) throws IOException {
        String fileName = htmlForm.fileName();
        FileObject fileObject = processingEnv
                .getFiler()
                .createResource(StandardLocation.CLASS_OUTPUT, "", fileName);
        try (Writer writer = fileObject.openWriter()) {
            writer.write("<form action=\""
                    + htmlForm.action()
                    + "\" method=\""
                    + htmlForm.method()
                    + "\">\n");
            for (Element enclosedElement : element.getEnclosedElements()) {
                HtmlInput htmlInput
                        = enclosedElement.getAnnotation(HtmlInput.class);
                if (htmlInput != null) {
                    writer.write("<input type=\""
                            + htmlInput.type()
                            + "\" name=\""
                            + htmlInput.name()
                            + "\" placeholder=\""
                            + htmlInput.placeholder()
                            + "\">\n");
                }
            }
            writer.write("<input type=\"submit\" value=\"Send\">");
            writer.write("</form>");
        }
    }
}

