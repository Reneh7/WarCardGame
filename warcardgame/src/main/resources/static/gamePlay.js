//===========================================START GAME / DEAL CARDS======================================================
function sendPlayer1DealCardsTest(playerSession){
    stompClient.send("/app/cards/dealPlayer1Test", {}, playerSession);
}

function sendPlayer2DealCardsTest(playerSession){
    stompClient.send("/app/cards/dealPlayer2Test", {}, playerSession);
}

function displayPlayer1CardsTest(cards) {
    const player1CardsContainer = document.getElementById('player1Cards');
    player1CardsContainer.innerHTML = '';
    cards.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.textContent = card.name + ' - ' + card.suit;
        player1CardsContainer.appendChild(cardElement);
    });
}

function displayPlayer2CardsTest(cards) {
    const player1CardsContainer = document.getElementById('player2Cards');
    player1CardsContainer.innerHTML = '';
    cards.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.textContent = card.name + ' - ' + card.suit;
        player1CardsContainer.appendChild(cardElement);
    });
}

function handlePlayCardButtonVisibility(sessionId) {
    var player1PlayedCardButton = document.getElementById('playCard1Button');
    var player2PlayedCardButton = document.getElementById('playCard2Button');
    var player1Session = document.getElementById('player1Session').innerText;
    var player2Session = document.getElementById('player2Session').innerText;

    if (sessionId === player1Session) {
        player1PlayedCardButton.style.display = 'inline-block';
        player2PlayedCardButton.style.display = 'none';
    } else if (sessionId === player2Session) {
        player2PlayedCardButton.style.display = 'inline-block';
        player1PlayedCardButton.style.display = 'none';
    }
}
//===========================================================PLAY CARD=================================================

function sendPlayer1PlayedCardMessageTest(player1Id) {
    stompClient.send("/app/cards/playCardPlayer1Test", {}, player1Id);
}

function displayPlayer1PlayedCardTest(card){
    const player1PlayedCardImage = document.getElementById('player1PlayedCardImage');
    player1PlayedCardImage.src = `/cards/${card.name}_${card.suit}.png`;
}


function sendPlayer2PlayedCardMessageTest(player1Id,player2Id,gameId) {
    var message = {
          player1Id : player1Id,
          player2Id : player2Id,
          gameId : gameId
    };

    stompClient.send("/app/cards/playCardPlayer2Test", {}, JSON.stringify(message));
}

function displayPlayer2PlayedCardTest(card){
    const player2PlayedCardImage = document.getElementById('player2PlayedCardImage');
    player2PlayedCardImage.src = `/cards/${card.name}_${card.suit}.png`;
}

//===========================================================DISPLAY CAPTURE CARDS===============================================

function displayPlayer1CapturedCards(capturedCards){
console.log("Inside displayPlayer1CapturedCards");
    const player1CapturedCards = document.getElementById('player1CapturedCards');
    player1CapturedCards.innerHTML = '';

    capturedCards.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.textContent = card.name + ' - ' + card.suit;
        player1CapturedCards.appendChild(cardElement);
    });

}

function displayPlayer2CapturedCards(capturedCards){
console.log("Inside displayPlayer2CapturedCards");
    const player2CapturedCards = document.getElementById('player2CapturedCards');
    player2CapturedCards.innerHTML = '';

    capturedCards.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.textContent = card.name + ' - ' + card.suit;
        player2CapturedCards.appendChild(cardElement);
    });
}

//===========================================================DEAL CAPTURED CARDS=================================================
function dealCapturedCards1(cards) {
    const player1CardsContainer = document.getElementById('player1Cards');
    player1CardsContainer.innerHTML = '';
    cards.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.textContent = card.name + ' - ' + card.suit;
        player1CardsContainer.appendChild(cardElement);
    });
}

function dealCapturedCards2(cards) {
    const player1CardsContainer = document.getElementById('player2Cards');
    player1CardsContainer.innerHTML = '';
    cards.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.textContent = card.name + ' - ' + card.suit;
        player1CardsContainer.appendChild(cardElement);
    });
}

//===========================================================WINNER MESSAGE=================================================

function displayWinner(winner){
     const winnerContainer = document.getElementById('winner');
     winnerContainer.textContent = "The winner is: " + winner;
}


//===========================================================LEAVE GAME=================================================

function sendLeaveGameMessage(gameId, playerSession,playerId) {
    var message = {
        gameId: gameId,
        playerSession: playerSession,
        playerId: playerId
    };
    if(message !== null) {
         stompClient.send("/app/games/leave", {}, JSON.stringify(message));
    }
}

function updateUIAfterLeaving(gameState) {
    if (gameState.player1 === null) {
        if(document.getElementById('player1') != null)
            document.getElementById('player1').innerText = "Player 1";
    } else if (gameState.player2 === null) {
        if(document.getElementById('player2') != null)
            document.getElementById('player2').innerText = "Player 2";
    }
}

//===========================================================PAGE BUTTONS=================================================

document.addEventListener('DOMContentLoaded', function() {

   // Start game button
   var startGameButton = document.getElementById('startGameButton');
   if (startGameButton) {
       startGameButton.addEventListener('click', function(event) {
           event.preventDefault();
           var gameId = document.getElementById('gameID').textContent.trim();
           var player1Session = document.getElementById('player1Session').innerText;
           var player2Session = document.getElementById('player2Session').innerText;

           sendPlayer1DealCardsTest(player1Session);
           sendPlayer2DealCardsTest(player2Session);

//           startGameButton.style.display = 'none';

       });
   }

   // Player 1 play Card button
   var player1PlayedCardButton = document.getElementById('playCard1Button');
   if (player1PlayedCardButton) {
       player1PlayedCardButton.addEventListener('click', function(event) {
           event.preventDefault();

         var player1Id = document.getElementById('player1Id').textContent.trim();
         sendPlayer1PlayedCardMessageTest(player1Id);
       });
   }

    // Player 2 play Card button
     var player2PlayedCardButton = document.getElementById('playCard2Button');
     if (player2PlayedCardButton) {
         player2PlayedCardButton.addEventListener('click', function(event) {
             event.preventDefault();

             var player1Id = document.getElementById('player1Id').textContent.trim();
             var player2Id = document.getElementById('player2Id').textContent.trim();
             var gameId = document.getElementById('gameID').textContent.trim();

             sendPlayer2PlayedCardMessageTest(player1Id,player2Id,gameId);
         });
     }

   // leave game button
   var leaveGameButton = document.getElementById('leaveGameButton');
   if (leaveGameButton) {
       leaveGameButton.addEventListener('click', function(event) {
           event.preventDefault();

           var sessionId = localStorage.getItem("sessionId");
           var player1Session = document.getElementById('player1Session').innerText;
           var player2Session = document.getElementById('player2Session').innerText;
           var player1Id = document.getElementById('player1Id').innerText;
           var player2Id = document.getElementById('player2Id').innerText;

           var gameId = document.getElementById('gameID').innerText;

           if (player1Session === sessionId) {
               sendLeaveGameMessage(gameId, player1Session,player1Id);
               localStorage.removeItem('sessionId');
           } else {
               sendLeaveGameMessage(gameId, player2Session,player2Id);
               localStorage.removeItem('sessionId');
           }
           window.location.href = '/';
       });
   }
});
