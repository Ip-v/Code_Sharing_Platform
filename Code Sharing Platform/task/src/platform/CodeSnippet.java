package platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class CodeSnippet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    private String code;
    private String dateTimeStr;
    private LocalDateTime dateTime;

    public CodeSnippet() {}

    public CodeSnippet(String jsonString) {
        String DATE_FORMATTER = "yyyy/MM/dd HH:mm:ss";
        this.setDateTime(LocalDateTime.now());
        this.setDateTimeStr(getDateTime().format(DateTimeFormatter.ofPattern(DATE_FORMATTER)));

        JSONObject obj = new JSONObject(jsonString);
        this.setCode(obj.getString("code"));
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public JSONObject getJSON() {
        return makeJson(this.getCode());
    }

    public String getDateTimeStr() {
        return dateTimeStr;
    }

    public void setDateTimeStr(String dateTimeStr) {
        this.dateTimeStr = dateTimeStr;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private JSONObject makeJson(String code){
        JSONObject obj = new JSONObject();
        obj.put("code", code);
        obj.put("date", getDateTimeStr());
        return obj;
    }


}

