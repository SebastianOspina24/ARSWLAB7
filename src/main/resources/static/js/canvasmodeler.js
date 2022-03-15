var puntos = [];

var canvasModel = (function () {
  return {
    init: function () {
      var canvas = document.getElementById("canvas");
      if (window.PointerEvent) {
        canvas.addEventListener("pointerdown", function (event) {
          var rec = canvas.getBoundingClientRect();
          var offset  = getOffset(canvas);
          console.log(puntos);
          if (puntos.length > 0) {
            var tempoint = {
              "x": event.pageX - offset.left,
              "y": event.pageY -  offset.top
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
};


function clscanvas() {
  var c = document.getElementById("canvas");
  var l = c.getContext("2d");
  l.beginPath();
  l.clearRect(0, 0, c.width, c.height);
  l.closePath();
}

function canvas(author, paint) {
  document.getElementById("blueprintname").innerHTML = paint;
  $.ajax({
    url: "http://localhost:8080/blueprints/" + author + "/" + paint
  }).then(function (data) {
    puntos = data.points;
    paintpoints()
  });
}

