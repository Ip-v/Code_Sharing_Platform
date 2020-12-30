package platform;

import freemarker.template.Template;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.StringWriter;
import java.util.*;

import org.json.*;


@SpringBootApplication
@RestController
public class CodeSharingPlatform {

    ArrayList<CodeSnippet> snippets = new ArrayList<>();

    public static void main(String[] args) {
        //Start app
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(path = "/code/new")
    public String newCode() {
        String out = "";
        try {
            out = new Scanner(new File("./Code Sharing Platform/task/src/resources/templates/code_new.ftlh")).useDelimiter("\\Z").next();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return out;
    }

    @GetMapping(path = "/api/code/{n}")
    public String getApiCodeN(@PathVariable int n) {
        return snippets.get(n).getJSON().toString();
    }

    @GetMapping(path = "/code/{n}")
    public String getHtmlCodeN(@PathVariable int n) {
        return snippets.get(n).HTML;
    }

    @GetMapping(path = "/api/code/latest")
    public String getApiCodeLatest() {
        JSONArray jo = returnLast10JsonObjects();
        return jo.toString();
    }

    private JSONArray returnLast10JsonObjects() {
        JSONArray jo = new JSONArray();
        if (snippets.size() > 10) {
            for (CodeSnippet snippet : snippets.subList(snippets.size()-10, snippets.size())) {
                jo.put(snippet.getJSON());
            }
        } else {
            for (CodeSnippet snippet : snippets) {
                jo.put(snippet.getJSON());
            }
        }
        return jo;
    }

    @GetMapping(path = "/code/latest")
    public String getHtmlLatest () {
        List<CodeSnippet> snippetsToView =
                snippets.size() > 10 ? snippets.subList(snippets.size()-10, snippets.size()) : snippets;
        Map<String, Object> root = new HashMap<>();
        root.put("program",snippetsToView);

        StringWriter out = new StringWriter();
        try {
            Template template = FreemarkerConfiguration.cfg.getTemplate("snippet_page_latest.ftlh");
            template.process(root, out);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return out.getBuffer().toString();
    }

    @PostMapping(path = "/api/code/new", consumes = "application/json", produces = "application/json")
    public String newCodeApi(@RequestBody String code) {
        int id = snippets.size() == 0 ? 0 : snippets.size();
        snippets.add(new CodeSnippet(code, id));
        return "{\"id\": \"" + id + "\"}";
    }

}
