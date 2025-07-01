let stompClient = null;
let currentGroupId = null;

function connectToGroup(groupId) {
    if (stompClient) stompClient.disconnect();
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/group.' + groupId, function (message) {
            showMessage(JSON.parse(message.body));
        });
        currentGroupId = groupId;
    });
}

function sendMessage() {
    const content = document.getElementById('message').value;
    stompClient.send("/app/group.sendMessage", {}, JSON.stringify({
        content: content,
        sender: username, // lấy từ session hoặc input
        groupId: currentGroupId,
        messageType: "CHAT"
    }));
}

// Gọi connectToGroup(groupId) khi user chọn group
// Gọi sendMessage() khi submit form chat