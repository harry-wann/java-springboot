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

        try {
            webSocket = new SockJS(url);
            client = Stomp.over(webSocket);
            // {} => headers
            client.connect({}, function (frame) {
                start.style.display = "none";
                chatDiv.style.display = "block";

                // 訂閱 => frame
                client.subscribe("/topic/public", function (message) {
                    console.log(message);
                    let body = JSON.parse(message.body);
                    receiveMessage(body);
                });
            }, connectFailure);
        } catch (e) {
            connectFailure(e)
        }
    }

    function connectFailure(error) {
        start.style.display = "block";
        chatDiv.style.display = "none";
        alert("連線失敗")
    }

    function receiveMessage(msg) {
        chatDiv.innerHTML = `${msg.content}`;
        //chatDiv.innerHTML = `${msg.content}`;

        let rowDiv = document.createElement("div");
        rowDiv.textContent = mesg.content;
        log.appendChild(rowDiv);
    }
}