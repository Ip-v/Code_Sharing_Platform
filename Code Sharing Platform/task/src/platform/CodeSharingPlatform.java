package platform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class CodeSharingPlatform {

    CodeSnippet codeSnippet = new CodeSnippet("public static void main(String[] args) {" +
            "SpringApplication.run(CodeSharingPlatform.class, args);" +
            "}");

    public static void main(String[] args) {
        SpringApplication.run(CodeSharingPlatform.class, args);
    }

    @GetMapping(path = "/code")
    public String html() {
        return codeSnippet.HTML;
    }

    @GetMapping(path = "/api/code")
    public String api() {
        return codeSnippet.JSON;
    }

    @GetMapping(path = "/code/new")
    public String newCode() {
        return "<html><head><title>Create</title></head>" +
                "<style>" +
                "#code_snippet {width: 600; font: 1em sans-serif; background-color: lightgrey; border: gray; border-style: initial;}" +
                "#load_date {color:green; font: 1em sans-serif;}" +
                "</style>" +
                "<body>" +
                "<script type=\"text/javascript\">" +
                "function send() {\n" +
                "    let object = {\n" +
                "        \"code\": document.getElementById(\"code_snippet\").value\n" +
                "    };\n" +
                "    \n" +
                "    let json = JSON.stringify(object);\n" +
                "    \n" +
                "    let xhr = new XMLHttpRequest();\n" +
                "    xhr.open(\"POST\", '/api/code/new', false)\n" +
                "    xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');\n" +
                "    xhr.send(json);\n" +
                "    \n" +
                "    if (xhr.status == 200) {\n" +
                "      alert(\"Success!\");\n" +
                "    }\n" +
                "}" +
                "</script>" +
                "<textarea id=\"code_snippet\"> ... </textarea>" +
                "<button id=\"send_snippet\" type=\"submit\" onclick=\"send()\">Submit</button>" +
                "</body>" +
                "</html>";
    }

    @PostMapping(path = "/api/code/new", consumes = "application/json", produces = "application/json")
    public String newCodeApi(@RequestBody String code) {
        codeSnippet = new CodeSnippet(code, true);
        return "{}";
    }

}
