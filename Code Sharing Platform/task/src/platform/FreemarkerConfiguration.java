package platform;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FreemarkerConfiguration {
    public String TemplatesFolder = "/resources/templates";
    Configuration cfg;

    public FreemarkerConfiguration() {
        //Freemarker cfg
        cfg = new Configuration(Configuration.VERSION_2_3_29);
        try {
            cfg.setDirectoryForTemplateLoading(new File(TemplatesFolder));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
    }
}
