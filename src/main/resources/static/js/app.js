function consulta() {
    var tbl = document.getElementById("tablablueprints");
    var rowCount = tbl.rows.length;
    for (var x = rowCount - 1; x > 0; x--) {
        tbl.deleteRow(x);
    }
    document.getElementById("nombreAutor").innerHTML = "";
    $.ajax({
        url: "http://localhost:8080/blueprints/" + document.getElementById("inputAuthor").value
    }).then(function (data) {
        var puntos = 0;
        let tm = document.getElementById("inputAuthor").value != "" ? "'s blueprints:" : "Todos";
        document.getElementById("nombreAutor").innerHTML = document.getElementById("inputAuthor").value + tm;
        data.forEach(element => {
            var tbl = document.getElementById("tablablueprints");
            let row = tbl.insertRow();
            let bpn = row.insertCell();
            let pn = row.insertCell();
            let btn = row.insertCell();
            bpn.innerHTML = element.name;
            pn.innerHTML = element.points.length;
            btn.innerHTML = "<Button class=\"btn\" onclick=\"canvas('"+element.author + "','" + element.name + "')\">OPEN</Button>";
            puntos += element.points.length;
            console.log(puntos);
        });
        document.getElementById("totalpoints").innerHTML = "Total user points: "+puntos;
    });
}

function clscanvas(){
    var c = document.getElementById("canvas");
    let l = c.getContext("2d");
    l.beginPath();
    l.clearRect(0, 0,c.width,c.height);
    l.closePath();
}

function canvas(author, paint) {
    clscanvas();
    document.getElementById("blueprintname").innerHTML = paint;
    $.ajax({
        url: "http://localhost:8080/blueprints/" + author + "/" + paint
    }).then(function (data) {
        var canvas = document.getElementById("canvas");
        let line = canvas.getContext("2d");
        line.beginPath();
        data.points.forEach(element => {
            line.moveTo(0, 0);
            line.lineTo(element.x, element.y);
        });
        line.closePath();
        line.stroke();
    });
}
