<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Latest</title>
    <link rel="stylesheet" type="text/css" href="/style.css">
</head>
<body>
    <ul>
        <#list program as codesnippet>
            <p><span id="load_date">${codesnippet.dateTimeStr !"no date"}</span></p>
            <pre id="code_snippet">${codesnippet.code !"code missing"}</pre>
        </#list>
    </ul>
</body>
</html>