var canvasModel=(function(){
    return {
      init:function(){
        var canvas = document.getElementById("canvas");
        console.info('initialized');
        if(window.PointerEvent) {
          canvas.addEventListener("pointerdown", function(event){
            let rec = canvas.getBoundingClientRect();
            postpoint(event.pageX-rec.x,event.pageY-rec.y);
          });
        }
      }    
    };
    
  })();
  
function postpoint(x,y){
};