var puntos = [];
var actualbprint;
var flag = false;


var canvasModel = (function () {
  return {
    init: function () {
      var canvas = document.getElementById("canvas");
      if (window.PointerEvent) {
        canvas.addEventListener("pointerdown", function (event) {
          var offset = getOffset(canvas);
          console.log(puntos);
          if (flag) {
            var tempoint = {
              "x": event.pageX - offset.left,
              "y": event.pageY - offset.top
            };
            puntos.push(tempoint);
            paintpoints();
          }
        });
      }
    }
  };

})();

function getOffset(obj) {
  var offsetLeft = 0;
  var offsetTop = 0;
  do {
    if (!isNaN(obj.offsetLeft)) {
      offsetLeft += obj.offsetLeft;
    }
    if (!isNaN(obj.offsetTop)) {
      offsetTop += obj.offsetTop;
    }
  } while (obj = obj.offsetParent);
  return { left: offsetLeft, top: offsetTop };
}

function paintpoints() {
  clscanvas();
  var data = puntos;
  var canvas = document.getElementById("canvas");
  var line = canvas.getContext("2d");
  line.beginPath();
  for (i = 0; i < data.length; i++) {
    line.moveTo(data[i].x, data[i].y);
    var j = i + 1 < data.length ? i + 1 : 0;
    line.lineTo(data[j].x, data[j].y);
  }
  line.closePath();
  line.stroke();
  flag = true;
};


function clscanvas() {
  var c = document.getElementById("canvas");
  var l = c.getContext("2d");
  l.beginPath();
  l.clearRect(0, 0, c.width, c.height);
  l.closePath();
  flag = false;
}

function canvas(author, paint) {
  document.getElementById("blueprintname").innerHTML = paint;
  actualbprint = {
    "author": author.toString(),
    "paint": paint.toString()
  }
  $.ajax({
    url: "http://localhost:8080/blueprints/" + author + "/" + paint
  }).then(function (data) {
    puntos = data.points;
    paintpoints();
  });
}


function saveblueprint() {
  var temjson = {
    "author": actualbprint.author,
    "points": puntos,
    "name": actualbprint.paint
  };
  $.ajax({
    url: "http://localhost:8080/blueprints/" + actualbprint.author + "/" + actualbprint.paint,
    type: 'PUT',
    data: JSON.stringify(temjson),
    contentType: "application/json"
  }).then(function () {
    alert("Felicidades actualizaste el blueprint: " + temjson.name + " del author: " + temjson.author);
    document.getElementById("inputAuthor").value = "";
    clscanvas();
    document.getElementById("blueprintname").innerHTML = "";
    document.getElementById("botonconsulta").click();
  });
}


function newblueprint() {
  clscanvas();
  var autor="";
  var name ="";
  while(autor == "" || autor == null){
    autor = prompt("Ingresa el nombre del autor");
  }
  while(name == "" || name == null){
    name = prompt("Ingrea el nombre de tu blueprint");
  }
  var temjson = {
    "author": autor,
    "points": [],
    "name": name
  };
  $.ajax({
    url: "http://localhost:8080/blueprints/creandoAndo",
    type: 'POST',
    data: JSON.stringify(temjson),
    contentType: "application/json"
  }).then(function () {
    document.getElementById("inputAuthor").value = "";
    document.getElementById("botonconsulta").click();
    canvas(temjson.author, temjson.name);
  });
}

function delblueprint(){
  if(confirm("Seguro quiere borrar esta blueprint: " + actualbprint.paint + " del author: " + actualbprint.author)){
    $.ajax({
      url: "http://localhost:8080/blueprints/" + actualbprint.author + "/" + actualbprint.paint,
      type: 'DELETE'
    }).then(function () {
      document.getElementById("inputAuthor").value = "";
      document.getElementById("blueprintname").innerHTML = "";
      clscanvas();
      document.getElementById("botonconsulta").click();
    });
  }
}
