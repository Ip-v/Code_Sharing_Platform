package platform;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Template;
import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CodeSnippet {
    private final String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";
    String HTML;
    String JSON;
    String DateTimeStr;
    LocalDateTime DateTime;

    @Autowired
    public FreemarkerConfiguration freemarkerConfiguration;

    public CodeSnippet(String code) {
        this.DateTime = LocalDateTime.now();
        this.DateTimeStr = DateTime.format(DateTimeFormatter.ofPattern(DATE_FORMATTER));
        this.HTML = makeHtml(code);
        this.JSON = makeJson(code);
    }

    public CodeSnippet(String jsonString, boolean isJson) {
        this.DateTimeStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER));

        JSONObject obj = new JSONObject(jsonString);
        String code = obj.getString("code");

        this.HTML = makeHtml(code);
        this.JSON = makeJson(code);
    }

    private String makeHtml(String code) {
        String out1 ="";
        try {
            Map root = new HashMap();
            root.put("code", code);
            root.put("date", DateTimeStr);

            Template template = freemarkerConfiguration.cfg.getTemplate("snippet_page.ftlh");
            Writer out = new OutputStreamWriter(System.out);
            template.process(root, out);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
       return "";
    }

    private String makeJson(String code){
        return "{" +
                "\"code\": \"" + code + "\", " +
                "\"date\": \"" + DateTimeStr + "\" " +
                "}";
    }

}

