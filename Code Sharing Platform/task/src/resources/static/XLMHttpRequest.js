 function send() {

    let views = parseInt(document.getElementById("views_restriction").value);
    if(views===0 || isNaN(views)){
        views = -1;
    }

    let object = {
        "code": document.getElementById("code_snippet").value,
        "time": parseInt(document.getElementById("time_restriction").value),
        "views": views
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