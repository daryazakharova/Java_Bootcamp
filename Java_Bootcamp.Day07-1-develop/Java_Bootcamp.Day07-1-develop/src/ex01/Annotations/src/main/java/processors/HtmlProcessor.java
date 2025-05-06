package processors;

import com.google.auto.service.AutoService;
import annotations.HtmlForm;
import annotations.HtmlInput;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.StringJoiner;

@SupportedAnnotationTypes({"annotations.HtmlForm","annotations.HtmlInput"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        boolean status = false;
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        if (!elements.isEmpty()) status = true;
        for (Element element : elements) {
            if (element instanceof TypeElement) {
                TypeElement typeElement = (TypeElement) element;
                HtmlForm htmlForm = typeElement.getAnnotation(HtmlForm.class);
                String fileName = htmlForm.fileName();
                StringJoiner stringForHtml = new StringJoiner("\">\n");
                stringForHtml.add("<form action = \"" + htmlForm.action() + "\" method = \"" + htmlForm.method());

                for (Element fieldElement : typeElement.getEnclosedElements()) {

                    if (fieldElement.getKind().isField() &&
                            fieldElement.getAnnotation(HtmlInput.class) != null) {
                        HtmlInput htmlInput = fieldElement.getAnnotation(HtmlInput.class);
                        stringForHtml.add("\t<input type = \"" + htmlInput.type()
                                + "\" name = \"" + htmlInput.name()
                                + "\" placeholder = \"" + htmlInput.placeholder());
                    }
                }
                stringForHtml.add("\t<input type=\"submit\" value=\"Send").add("</form>");
                try {
                    saveToFile(fileName, stringForHtml);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return status;
    }

    public void saveToFile(String fileName, StringJoiner stringForHtml) throws IOException {
        Path outputPath = Paths.get("target/classes", fileName);
        BufferedWriter bufferedWriter = Files.newBufferedWriter(outputPath);
        bufferedWriter.write(stringForHtml.toString());
        bufferedWriter.close();
    }
}
