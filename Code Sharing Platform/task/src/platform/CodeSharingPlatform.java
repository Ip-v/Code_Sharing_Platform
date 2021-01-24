package platform;

import freemarker.template.Template;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.StringWriter;
import java.util.*;
import java.util.List;

import org.json.*;


//TODO GET /code/latest should return HTML that contains 10 most recently uploaded code snippets. Use the title Latest for this page.

@SpringBootApplication
@RestController
public class CodeSharingPlatform {

    ArrayList<CodeSnippet> snippets = new ArrayList<>();
    private final CodeRepository codeRepository;

    public CodeSharingPlatform (CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public static void main(String[] args) {
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
    public String getApiCodeN(@PathVariable Integer n) {
        return codeRepository.findById(n).get().getJSON().toString();
    }

    @GetMapping(path = "/code/{n}")
    public String getHtmlCodeN(@PathVariable Integer n) {
        Optional<CodeSnippet> optional = codeRepository.findById(n);
        if (optional.isPresent()) {
            CodeSnippet cs = optional.get();
            StringWriter out = new StringWriter();
            try {
                Map root = new HashMap();
                root.put("code", cs.getCode());
                root.put("date", cs.getDateTimeStr());
                Template template = FreemarkerConfiguration.cfg.getTemplate("snippet_page.ftlh");
                template.process(root, out);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
          return out.getBuffer().toString();
        } else {
            return  "empty";
        }
    }

    @GetMapping(path = "/api/code/latest")
    public String getApiCodeLatest() {
        JSONArray jo = returnLast10JsonObjects();
        return jo.toString();
    }

    private JSONArray returnLast10JsonObjects() {
        List<CodeSnippet> snippetsToView = codeRepository.findTop10ByOrderByDateTimeDesc();
        JSONArray jo = new JSONArray();
        for(CodeSnippet snippet : snippetsToView) {
            jo.put(snippet.getJSON());
        }
        return jo;
    }

    @GetMapping(path = "/code/latest")
    public String getHtmlLatest () {

        List<CodeSnippet> snippetsToView = codeRepository.findTop10ByOrderByDateTimeDesc();

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
//TODO test
    @PostMapping(path = "/api/code/new", consumes = "application/json", produces = "application/json")
    public String newCodeApi(@RequestBody String code) {
        CodeSnippet snippet = new CodeSnippet(code);
        codeRepository.save(snippet);
        return "{\"id\": \"" + snippet.id + "\"}";
    }



}
