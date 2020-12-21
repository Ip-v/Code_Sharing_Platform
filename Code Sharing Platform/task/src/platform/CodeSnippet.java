package platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.json.*;

public class CodeSnippet {
    String DATE_FORMATTER= "yyyy/MM/dd HH:mm:ss";
    String HTML;
    String JSON;
    String Date;

    public CodeSnippet(String code) {
        this.Date = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER));
        this.HTML = makeHtml(code);
        this.JSON = makeJson(code);
    }

    public CodeSnippet(String jsonString, boolean isJson) {
        this.Date = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMATTER));

        JSONObject obj = new JSONObject(jsonString);
        String code = obj.getString("code");

        this.HTML = makeHtml(code);
        this.JSON = makeJson(code);
    }

    private String makeHtml(String code){
       return "<html><head><title>Code</title></head>" +
               "<style>" +
               "#code_snippet {width: 600; font: 1em sans-serif; background-color: lightgrey; border: gray; border-style: initial;}" +
               "#load_date {color:green; font: 1em sans-serif;}" +
               "</style>" +
               "<body>" +
               "<span id=\"load_date\">"+ Date +"</span>" +
               "<pre id=\"code_snippet\">" + code + "</pre>" +
               "</body>" +
               "</html>";
    }

    private String makeJson(String code){
        return "{" +
                "\"code\": \"" + code + "\", " +
                "\"date\": \"" + Date + "\" " +
                "}";
    }

}

