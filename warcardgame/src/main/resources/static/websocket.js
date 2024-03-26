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

            redirectToGameplayPage(newGameId);
        });

         stompClient.subscribe('/topic/dealCardsPlayer1', function(message) {
            var response = JSON.parse(message.body);
            var cards = response.cards;
            var player1Session = response.playerSession;
            var sessionId = localStorage.getItem("sessionId");

            if(sessionId === player1Session){
                 displayPlayer1Cards(cards);
                 handlePlayCardButtonVisibility(player1Session);
            }
         });
         stompClient.subscribe('/topic/dealCardsPlayer2', function(message) {
            var response = JSON.parse(message.body);
            var cards = response.cards;
            var player2Session = response.playerSession;
            var sessionId = localStorage.getItem("sessionId");

            if(sessionId === player2Session){
                displayPlayer2Cards(cards);
                handlePlayCardButtonVisibility(player2Session);
            }
         });



         stompClient.subscribe('/topic/playCardPlayer1', function(message) {
            var card = JSON.parse(message.body);
            displayPlayer1PlayedCard(card);
         });
         stompClient.subscribe('/topic/playCardPlayer2', function(message) {
            var card = JSON.parse(message.body);
            displayPlayer2PlayedCard(card);
         });


        stompClient.subscribe('/topic/leaveGame', function(message) {
             var leaveData = JSON.parse(message.body);
        });
        stompClient.subscribe('/topic/updateGameAfterLeave', function(message) {
            var gameState = JSON.parse(message.body);
            updateUIAfterLeaving(gameState);
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

document.addEventListener('DOMContentLoaded', function() {
    connectToWebSocket();
});
