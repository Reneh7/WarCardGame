var stompClient = null;

function connectToWebSocket() {
    if (stompClient !== null) {
        return;
    }

    var socket = new SockJS('/war-game');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log("Connected:", frame);

        stompClient.subscribe('/topic/createGame', function(message) {
            var body = JSON.parse(message.body);
            var newGameId = body.gameId;
            var createdPlayerSessionId = body.createdPlayerSessionId;

            if (createdPlayerSessionId === getSessionId()) {
                redirectToGameplayPage(newGameId);
            }
        });

        stompClient.subscribe('/topic/joinGame', function(message) {
            var response = JSON.parse(message.body);
            var newGameId = response.gameId;
            var joinedPlayerSessionId = response.joinedPlayerSessionId;

            redirectJoinedPlayerToGameplayPage(newGameId, joinedPlayerSessionId);
        });

        stompClient.subscribe('/topic/updateGame', function(message) {
            var gameState = JSON.parse(message.body);
            updateUI(gameState);
        });

        stompClient.subscribe('/topic/dealCardsPlayer1', function(message) {
            var cards = JSON.parse(message.body);
            var cardsHtml = '';
            cards.forEach(function(card) {
                cardsHtml += '<p>' + card.name + ' - ' + card.suit + '</p>';
            });
            document.getElementById('player1Cards').innerHTML = cardsHtml;
        });

        stompClient.subscribe('/topic/dealCardsPlayer2', function(message) {
            var cards = JSON.parse(message.body);
            var cardsHtml = '';
            cards.forEach(function(card) {
                cardsHtml += '<p>' + card.name + ' - ' + card.suit + '</p>';
            });
            document.getElementById('player2Cards').innerHTML = cardsHtml;
        });

        stompClient.subscribe('/topic/leaveGame', function(message) {
            var leaveData = JSON.parse(message.body);
            localStorage.removeItem('sessionId' + leaveData.sessionId); // it does not remove the key value pair
        });
    });
}

function disconnectWebSocket() {
    if (stompClient !== null) {
        stompClient.disconnect();
        stompClient = null;
        console.log("Disconnected");
    }
}

// Ensure WebSocket connection when DOM content is loaded
document.addEventListener('DOMContentLoaded', function() {
    connectToWebSocket();
});
