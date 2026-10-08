window.onload = function(){
    let start = document.getElementById("start");
    let chatDiv = document.getElementById("chatDiv");
    let mesg = document.getElementById("mesg");
    let send = document.getElementById("send");
    let log = document.getElementById("log");

    let client;
    let webSocket;
    // let url = "/ws-chat?token=" + encodeURIComponent("Bearer " + token)
    let url = "/ws-chat";

    start.style.display = "block";
    chatDiv.style.display = "none";

    start.addEventListener("click", function(){
        connect(url);
    });

    send.addEventListener("click", function(){
        let msg = mesg.value.trim();
        client.send("/app/chat/send", {}, msg);
        mesg.value = "";
    });

    function connect(url){
        console.log("Connecting...");

        webSocket = new SocketJS(url);
        let client = Stomp.over(webSocket);
        client.connect({}, function(frame) {
            console.log("Connected!");
            start.style.display = "none";
            chatDiv.style.display = "block";

            client.subscribe("/topic/public", function(msg) {
                console.log(msg);
                let body = JSON.parse(msg);
                receiveMessage(body)
            });
        });
    }

    function receiveMessage(msg) {
        chatDiv.innerHTML = `${msg.content}`
    }
}