<html>
    <head>
        <title>Create</title>
    </head>
    <script type="text/javascript" src="/XLMHttpRequest.js"></script>
    <link rel="stylesheet"
           target="_blank" href="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/styles/default.min.css">
    <script src="//cdn.jsdelivr.net/gh/highlightjs/cdn-release@10.2.1/build/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
    <body>
        <style>
            #code_snippet {width: 600; font: 1em sans-serif; background-color: lightgrey; border: gray; border-style: initial;}
            #load_date {color:green; font: 1em sans-serif;}
        </style>
        <script type="text/javascript">"
            function send() {
                let object = {
                    "code": document.getElementById("code_snippet").value
                };

                let json = JSON.stringify(object);

                let xhr = new XMLHttpRequest();
                xhr.open("POST", '/api/code/new', false)
                xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
                xhr.send(json);

                if (xhr.status == 200) {
                  alert("Success!");
                }
            }
        </script>
        <textarea id="code_snippet"><code> ... </code></textarea>
        <button id="send_snippet" type="submit" onclick="send()">Submit</button>
    </body>
</html>