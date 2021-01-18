package platform;

import freemarker.template.Template;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.io.StringWriter;
import java.util.*;
import java.util.List;

import org.json.*;

//TODO GET /api/code/N should return JSON with the N-th uploaded code snippet.
//TODO GET /code/N should return HTML that contains the N-th uploaded code snippet.
//TODO POST /api/code/new should take a JSON object with a single field code, use it as the current code snippet, and return JSON with a single field id. ID is the unique number of the code snippet that can help you access it via the endpoint GET /code/N.
//TODO GET /code/new should be the same as in the second and third stages.
//TODO GET /api/code/latest should return a JSON array with 10 most recently uploaded code snippets sorted from the newest to the oldest.
//TODO GET /code/latest should return HTML that contains 10 most recently uploaded code snippets. Use the title Latest for this page.

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
            out = new Scanner(new File("C:\\Users\\Ivan\\IdeaProjects\\Code_Sharing_Platform\\Code Sharing Platform\\task\\src\\resources\\templates\\code_new.ftlh")).useDelimiter("\\Z").next();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return out;
    }

    @GetMapping(path = "/api/code/{n}")
    public String getApiCodeN(@PathVariable int n) {
        return snippets.size() > 0 ? snippets.get(n - 1).getJSON().toString() : "empty";
    }

    @GetMapping(path = "/code/{n}")
    public String getHtmlCodeN(@PathVariable int n) {
        return snippets.size() > 0 ? snippets.get(n - 1).HTML : "empty";
    }

    @GetMapping(path = "/api/code/latest")
    public String getApiCodeLatest() {
        JSONArray jo = returnLast10JsonObjects();
        return jo.toString();
    }

    private JSONArray returnLast10JsonObjects() {
        List<CodeSnippet> snippetsToView =
                snippets.size() > 10 ? snippets.subList(snippets.size()-10, snippets.size()) : snippets;


        JSONArray jo = new JSONArray();
        for(CodeSnippet snippet : sortDateDescending(snippetsToView)) {
            jo.put(snippet.getJSON());
        }
        return jo;
    }

    @GetMapping(path = "/code/latest")
    public String getHtmlLatest () {
        List<CodeSnippet> snippetsToView =
                snippets.size() > 10 ? snippets.subList(snippets.size()-10, snippets.size()) : snippets;

        Map<String, Object> root = new HashMap<>();
        root.put("program",sortDateDescending(snippetsToView));

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
        int id = snippets.size() == 0 ? 1 : snippets.size() + 1;
        snippets.add(new CodeSnippet(code, id));
        System.out.println("SNIPPET ADDED ID " + id + ", SNIPPETS size is " + snippets.size());
        return "{\"id\": \"" + id + "\"}";
    }

    public static List<CodeSnippet> sortDateDescending(List<CodeSnippet> codes) {
        List<CodeSnippet> sorted = new ArrayList<>(codes);
        sorted.sort((a, b) -> -1 * (a.getDateTime().compareTo(b.getDateTime())));
        return sorted;
    }

}
