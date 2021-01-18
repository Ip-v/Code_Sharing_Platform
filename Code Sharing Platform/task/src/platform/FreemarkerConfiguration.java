package platform;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;
import org.springframework.stereotype.Component;
import java.io.File;

@Component
public class FreemarkerConfiguration {
    public String TemplatesFolder = "C:\\Users\\Ivan\\IdeaProjects\\Code_Sharing_Platform\\Code Sharing Platform\\task\\src\\resources\\templates";
    static Configuration  cfg;

    public FreemarkerConfiguration() {
        //Freemarker cfg
        cfg = new Configuration(Configuration.VERSION_2_3_29);
        try {
            cfg.setDirectoryForTemplateLoading(new File(TemplatesFolder));
        } catch (Exception ex) {
            final String dir = System.getProperty("user.dir");
            System.out.println("Current DIR = " + dir);
            System.out.println("Exception: " + ex.getMessage());
        }
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
    }

    public Configuration getCfg(){
        return cfg;
    }

}
