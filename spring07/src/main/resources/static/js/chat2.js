/*
    1 SockJS => 1 Client -> 2 Subscribe (multi)
    Java: SEND: /app/chat/java/send
    Spring: SEND: /app/chat/spring/send
 */
window.onload = function(){
    let start = document.getElementById("start");
    let chatDiv = document.getElementById("chatDiv");

    let msg = document.getElementById("msg");
    let send = document.getElementById("send");
    let log = document.getElementById("log");

    let springMsg = document.getElementById("springMesg");
    let springSend = document.getElementById("springSend");
    let springLog = document.getElementById("springLog");

    let webSocket;
    //let url = "/ws-chat?token=" + encodeURIComponent('Bearer ' + token);
    let url = "/ws-chat";
    let client = null;

    start.style.display = "block";
    chatDiv.style.display = "none";

    //------------------------------------------
    start.addEventListener("click", function(){
        connect(url);
    });
    send.addEventListener("click", function(){
        sendMessage("java", msg)
    });
    springSend.addEventListener("click", function(){
        sendMessage("spring", springMsg)
    });
    //--------------------------------------------
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
                client.subscribe("/topic/rooms/java", function (message) {
                    receiveMessage(message, log);
                });

                client.subscribe("/topic/rooms/spring", function (message) {
                    receiveMessage(message, springLog);
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

    //----------------------------------------------------
    function sendMessage(roomId, input) {
        let content = input.value.trim();
        if (!content) return;
        client.send(`/app/chat/${roomId}/send`,{}, content);
        input.value = "";
    }

    function receiveMessage(mesg, targetLog) {
        let body = JSON.parse(mesg.body);
        let rowDiv = document.createElement("div");
        rowDiv.textContent = body.content;
        targetLog.appendChild(rowDiv);
    }
}