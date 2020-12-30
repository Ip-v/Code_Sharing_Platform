package platform;

import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Template;
import org.json.*;

public class CodeSnippet {
    int Id;
    String HTML;
    private String Code;
    private JSONObject JSON;
    private String DateTimeStr;
    private LocalDateTime DateTime;

    public CodeSnippet(String jsonString, int Id) {
        String DATE_FORMATTER = "yyyy/MM/dd HH:mm:ss";
        this.setDateTime(LocalDateTime.now());
        this.setDateTimeStr(getDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)));

        JSONObject obj = new JSONObject(jsonString);
        this.setCode(obj.getString("code"));
        this.Id = Id;
        this.HTML = makeHtml(this.getCode());
        this.setJSON(makeJson(this.getCode()));
    }

    public LocalDateTime getDateTime() {
        return DateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        DateTime = dateTime;
    }

    public JSONObject getJSON() {
        return JSON;
    }

    public void setJSON(JSONObject JSON) {
        this.JSON = JSON;
    }

    public String getDateTimeStr() {
        return DateTimeStr;
    }

    public void setDateTimeStr(String dateTimeStr) {
        DateTimeStr = dateTimeStr;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    private String makeHtml(String code) {
        StringWriter out = new StringWriter();
        try {
            Map root = new HashMap();
            root.put("code", code);
            root.put("date", getDateTimeStr());
            Template template = FreemarkerConfiguration.cfg.getTemplate("snippet_page.ftlh");
            template.process(root, out);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
       return out.getBuffer().toString();
    }

    private JSONObject makeJson(String code){
        JSONObject obj = new JSONObject();
        obj.put("code", code);
        obj.put("date", getDateTimeStr());
        return obj;
    }


}

