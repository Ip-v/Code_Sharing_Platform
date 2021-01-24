package platform;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.json.*;

import javax.persistence.*;


@Entity
public class CodeSnippet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "VARCHAR(255)", insertable = false, updatable = false)
    UUID id;

    private String code;
    private String dateTimeStr;
    private LocalDateTime dateTime;
    private LocalDateTime time;
    private Long views;
    private boolean secret;

    public Long getTime() {
        Long out = LocalDateTime.now().until(time, ChronoUnit.SECONDS);
        return out > 0 ? out : 0;
    }

    public void setTime(Long time) {
        this.time = dateTime.plusSeconds(time);
    }

    public boolean isSecret() {
        return secret;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        this.views = views;
    }
    public void minusView() {
        views--;
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

    public CodeSnippet() {}

    public CodeSnippet(String jsonString) {
        this.setDateTime(LocalDateTime.now());
        this.setDateTimeStr(getDateTime().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
        JSONObject obj = new JSONObject(jsonString);
        this.setCode(obj.getString("code"));
        this.setTime(obj.getLong("time"));
        this.setViews(obj.getLong("views"));
        this.secret = obj.getLong("time") > 0 || getViews() > 0;
    }

    private JSONObject makeJson(String code){
        JSONObject obj = new JSONObject();
        obj.put("code", code);
        obj.put("date", getDateTimeStr());
        obj.put("views", getViews());
        obj.put("time", getTime());
        return obj;
    }


}

