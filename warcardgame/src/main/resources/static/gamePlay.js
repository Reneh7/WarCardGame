//===========================================START GAME / DEAL CARDS======================================================
function sendPlayer1DealCards(gameId){
    stompClient.send("/app/cards/dealPlayer1", {}, gameId);
}

function sendPlayer2DealCards(gameId){
    stompClient.send("/app/cards/dealPlayer2", {}, gameId);
}

function displayPlayer1Cards(cards) {
    const player1CardsContainer = document.getElementById('player1Cards');
    player1CardsContainer.innerHTML = '';
    cards.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.textContent = card.name + ' - ' + card.suit;
        player1CardsContainer.appendChild(cardElement);
    });
}

function displayPlayer2Cards(cards) {
    const player1CardsContainer = document.getElementById('player2Cards');
    player1CardsContainer.innerHTML = '';
    cards.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.textContent = card.name + ' - ' + card.suit;
        player1CardsContainer.appendChild(cardElement);
    });
}

//===========================================================PLAY CARD=================================================

function sendPlayer1PlayedCardMessage(player1Id) {
    stompClient.send("/app/cards/playCardPlayer1", {}, player1Id);
}

function displayPlayer1PlayedCard(card){
    const player1PlayedCard = document.getElementById('player1PlayedCard');
    player1PlayedCard.innerHTML = '';

    const cardElement = document.createElement('div');
    cardElement.textContent = card.name + ' - ' + card.suit;

    player1PlayedCard.appendChild(cardElement);
}

function sendPlayer2PlayedCardMessage(player2Id) {
    stompClient.send("/app/cards/playCardPlayer2", {}, player2Id);
}

function displayPlayer2PlayedCard(card){
    const player2PlayedCard = document.getElementById('player2PlayedCard');
    player2PlayedCard.innerHTML = '';

    const cardElement = document.createElement('div');
    cardElement.textContent = card.name + ' - ' + card.suit;

    player2PlayedCard.appendChild(cardElement);
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

           sendPlayer1DealCards(gameId);
           sendPlayer2DealCards(gameId);
       });
   }

   // Player 1 play Card button
   var player1PlayedCardButton = document.getElementById('playCard1Button');
   if (player1PlayedCardButton) {
       player1PlayedCardButton.addEventListener('click', function(event) {
           event.preventDefault();
           var player1Id = document.getElementById('player1Id').textContent.trim();
           sendPlayer1PlayedCardMessage(player1Id);
       });
   }

    // Player 2 play Card button
      var player2PlayedCardButton = document.getElementById('playCard2Button');
      if (player2PlayedCardButton) {
          player2PlayedCardButton.addEventListener('click', function(event) {
              event.preventDefault();
              var player2Id = document.getElementById('player2Id').textContent.trim();
              sendPlayer2PlayedCardMessage(player2Id);
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
