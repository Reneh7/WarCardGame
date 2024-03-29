var stompClient = null;
var player1TurnInfo = null;
var player2TurnInfo = null;

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

        stompClient.subscribe('/topic/dealCardsPlayer1Test', function(message) {
            var response = JSON.parse(message.body);
            var cards = response.cards;
            var player1Session = response.playerSession;
            var sessionId = localStorage.getItem("sessionId");

            if(sessionId === player1Session){
                displayPlayer1CardsTest(cards);
                handlePlayCardButtonVisibility(player1Session);
            }
        });
        stompClient.subscribe('/topic/dealCardsPlayer2Test', function(message) {
            var response = JSON.parse(message.body);
            var cards = response.cards;
            var player2Session = response.playerSession;
            var sessionId = localStorage.getItem("sessionId");

            if(sessionId === player2Session){
                displayPlayer2CardsTest(cards);
                handlePlayCardButtonVisibility(player2Session);
            }
        });




        stompClient.subscribe('/topic/playCardPlayer1Test', function(message) {
            var response = JSON.parse(message.body);
            var card = response.card;
            var turn = response.turn;
            console.log("player 1 turn: ", turn);
            var playerSession = response.playerSession;
            var currentSession = localStorage.getItem("sessionId");

            if (turn === true) {
                displayPlayer1PlayedCardTest(card);
            } else if (turn === false && playerSession === currentSession ) {
                alert("It's not your turn!");
            }
        });
        stompClient.subscribe('/topic/playCardPlayer2Test', function(message) {
             var response = JSON.parse(message.body);
             var card = response.card;
             var turn = response.turn;
             console.log("player 2 turn: ", turn);
             var playerSession = response.playerSession;
             var currentSession = localStorage.getItem("sessionId");

            if (turn === true){
                displayPlayer2PlayedCardTest(card);
            } else if (turn === false && playerSession === currentSession ){
                alert("It's not your turn!");
            }
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
