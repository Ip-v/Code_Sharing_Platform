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


//todo POST /api/code/new should take a JSON object with a field code and two other fields:
    //todo 1. time field contains the time (in seconds) during which the snippet is accessible.
    //todo 2. views field contains a number of views allowed for this snippet.
//todo Remember, that 0 and negative values should correspond to the absence of the restriction.
//todo GET /code/new should contain two elements on top of the others:
//todo 1. <input id="time_restriction" type="text"/> should contain the time restriction.
//todo 2. <input id="views_restriction" type="text"/> should contain the views restriction
//todo
//todo Remember that POST request should contain numbers, not strings.
//todo GET /api/code/latest and GET /code/latest should not return any restricted snippets.
//todo GET /api/code/UUID should not be accessible if one of the restrictions is triggered. Return 404 Not Found in this case and all the cases when no snippet with such a UUID was found.
//todo GET /api/code/UUID should show what restrictions apply to the code piece. Use the keys time and views for that. A zero value (0) should correspond to the absence of the restriction.
//todo 1. time field contains the time (in seconds) during which the snippet is accessible.
//todo 2. views field shows how many additional views are allowed for this snippet (excluding the current one).
//todo GET /code/UUID should contain the following elements:
//todo 1.<span id="time_restriction"> ... </span> in case the time restriction is applied.
//todo 2. <span id="views_restrict ion"> ... </span> in case the views restriction is applied.

//todo Note: if only one of the restrictions is applied, you should show only one of the above elements.


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
    public String getApiCodeN(@PathVariable UUID n) {
        Optional<CodeSnippet> optional = codeRepository.findById(n);
        if (optional.isPresent()) {
            CodeSnippet cs = optional.get();
            if (cs.isSecret()) {
                if (cs.getViews() > 0 | cs.getTime() > 0) {
                    cs.minusView();
                    codeRepository.save(cs);
                    return cs.getJSON().toString();
                } else {
                    return "404 Not Found";
                }
            }
            else return cs.getJSON().toString();
        } else {
            return "404 Not Found";
        }
    }

    @GetMapping(path = "/code/{n}")
    public String getHtmlCodeN(@PathVariable UUID n) {
        Optional<CodeSnippet> optional = codeRepository.findById(n);
        if (optional.isPresent()) {
            CodeSnippet cs = optional.get();
            StringWriter out = new StringWriter();
            Map root = new HashMap();
            if(cs.isSecret()) {
                if (cs.getTime() > 0 | cs.getViews() > 0) {
                    root.put("code", cs.getCode());
                    root.put("date", cs.getDateTimeStr());
                    if (cs.getTime() > 0)
                        root.put("time", cs.getTime());
                    if (cs.getViews() > 0)
                        cs.minusView();
                        codeRepository.save(cs);
                        root.put("views", cs.getViews());
                } else {
                    return "404 Not Found";
                }
            } else {
                root.put("code", cs.getCode());
                root.put("date", cs.getDateTimeStr());
            }
            try {
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
        List<CodeSnippet> snippetsToView = codeRepository.findTop10BySecretFalseOrderByDateTimeDesc();
        JSONArray jo = new JSONArray();
        for(CodeSnippet snippet : snippetsToView) {
            jo.put(snippet.getJSON());
        }
        return jo;
    }

    @GetMapping(path = "/code/latest")
    public String getHtmlLatest () {

        List<CodeSnippet> snippetsToView = codeRepository.findTop10BySecretFalseOrderByDateTimeDesc();

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
        CodeSnippet snippet = new CodeSnippet(code);
        codeRepository.save(snippet);
        return "{\"id\": \"" + snippet.id + "\"}";
    }



}
