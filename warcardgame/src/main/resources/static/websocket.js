var stompClient = null;

function connectToWebSocket() {
    if (stompClient !== null) {
        return;
    }

    var socket = new SockJS('/war-game');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function(frame) {
        console.log("Connected:", frame);


        // CREATE/JOIN
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
        stompClient.subscribe('/topic/gameNotFound', function(message) {
            var errorMessage = message.body;
            var gameNotFoundMessage = document.getElementById('gameNotFound');
            gameNotFoundMessage.innerHTML = errorMessage;
            showGameIdForm();
            submitGameIdButton.disabled = true;
        });


        // DEALING CARDS
        stompClient.subscribe('/topic/dealCardsPlayer1Test', function(message) {
            var response = JSON.parse(message.body);
            var cards = response.cards;
            var player1Session = response.playerSession;
            var sessionId = localStorage.getItem("sessionId");

            if(sessionId === player1Session){
                 handlePlayCardButtonVisibility(player1Session);
            }
        });
        stompClient.subscribe('/topic/dealCardsPlayer2Test', function(message) {
            var response = JSON.parse(message.body);
            var cards = response.cards;
            var player2Session = response.playerSession;
            var sessionId = localStorage.getItem("sessionId");

            if(sessionId === player2Session){
                handlePlayCardButtonVisibility(player2Session);
            }
        });


        //PLAYING THE DEALT CARDS
        stompClient.subscribe('/topic/playCardPlayer1Test', function(message) {
            var response = JSON.parse(message.body);
            var card = response.card;
            var turn = response.turn;
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
             var playerSession = response.playerSession;
             var currentSession = localStorage.getItem("sessionId");

            if (turn === true){
                displayPlayer2PlayedCardTest(card);
            } else if (turn === false && playerSession === currentSession ){
                alert("It's not your turn!");
            }
        });



         // CAPTURING CARDS
         stompClient.subscribe('/topic/capturedCards/player1', function(message) {
             var capturedCards = JSON.parse(message.body);
             displayPlayer1CapturedCards(capturedCards);
         });
         stompClient.subscribe('/topic/capturedCards/player2', function(message) {
             var capturedCards = JSON.parse(message.body);
             displayPlayer2CapturedCards(capturedCards);
         });




         // DEALING CAPTURED CARDS
         stompClient.subscribe('/topic/dealCapturedCards1', function(message) {
             var response = JSON.parse(message.body);
             var cards = response.cards;
             var player1Session = response.playerSession;
             var sessionId = localStorage.getItem("sessionId");

             if(sessionId === player1Session){
                dealCapturedCards1(cards);
             }

         });
         stompClient.subscribe('/topic/dealCapturedCards2', function(message) {
             var response = JSON.parse(message.body);
             var cards = response.cards;
             var player2Session = response.playerSession;
             var sessionId = localStorage.getItem("sessionId");

             if(sessionId === player2Session){
                dealCapturedCards2(cards);
             }
         });



        // DISPLAY WINNER
        stompClient.subscribe('/topic/game/winner', function(message) {
             var winner =  message.body;
             displayWinner(winner);
        });


        // LEAVING THE GAME
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
