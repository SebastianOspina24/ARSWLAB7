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
        });
        document.getElementById("totalpoints").innerHTML = "Total user points: "+puntos;
    });
}
