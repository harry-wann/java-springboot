window.onload = function(){
    // let url = "ws://10.0.100.160:8080/wb";
    let url = "ws://localhost:8080/wb";
    let webSocket = new WebSocket(url);
    let myDrawer = document.getElementById("myDrawer");
    let ctx = myDrawer.getContext("2d");
    let isConnect = false;

    //-------------------------------------------
    webSocket.onopen = function(){
        isConnect = true;
    }

    webSocket.onclose = function(){
        isConnect = false;
    }

    webSocket.onmessage = function(event){
        if (isConnect) {
            let msgObj = JSON.parse(event.data);
            if (msgObj.isClear){
                clear();
            } else {
                if (msgObj.isNewLine){
                    newLine(msgObj.x, msgObj.y);
                } else {
                    drawLine(msgObj.x, msgObj.y);
                }
            }
        }
    }

    //----------------------------------------
    function clear(){
        ctx.clearRect(0,0, myDrawer.width, myDrawer.height);
    }

    function newLine(x, y){
        ctx.lineWidth = 4;
        ctx.beginPath();
        ctx.moveTo(x, y);
    }

    function drawLine(x, y){
        ctx.lineTo(x, y);
        ctx.stroke();
    }
}