var ws = new WebSocket("ws://localhost:8082/hao");


ws.onopen = function (evt) {
    console.log("Connection open ...");
  };
  var that = this;
  ws.onmessage = function (evt) {
    console.log("Received Message: " + evt.data);

    var data = JSON.parse(evt.data);
   if(data.num!=null){
    var num = document.getElementById("num");
    num.innerHTML = data.num;
    }
    if(data.list!=null){
      var list = document.getElementById("list");
      var content = '';
      for(var i = 0;i<data.list.length;i++){
         content +=data.list[i] + '<br>';
      }
      list.innerHTML = content ;
      }
      
  };

  ws.onclose = function (evt) {
    console.log("Connection closed.");
  };


