//===========================================ANIMATION======================================================
var player1CardSprite;
var player2CardSprite;
var game;
var config;


function preload() {
    const suits = ['Clubs', 'Spades', 'Hearts', 'Diamonds'];
        const values = ['2', '3', '4', '5', '6', '7', '8', '9', '10', 'Jack', 'Queen', 'King', 'Ace'];

        for (const suit of suits) {
            for (const value of values) {
                const cardName = `${value}_${suit}`;
                this.load.image(cardName, `/cards/${cardName}.png`, { frameWidth: 100, frameHeight: 150 });
            }
        }
}

function create() {
    player1CardSprite = this.add.sprite(400, 300, "2_Clubs");
    player1CardSprite.setOrigin(0.5);
    player1CardSprite.setScale(0);

    player2CardSprite = this.add.sprite(400, 300, "2_Clubs");
    player2CardSprite.setOrigin(0.5);
    player2CardSprite.setScale(0);

}

function playCardAnimation(scene,cardSprite,endPosition) {
    const startX = endPosition.startX;
    const startY = endPosition.startY;
    const endX = endPosition.endX;
    const endY = endPosition.endY;

    cardSprite.setX(startX);
    cardSprite.setY(startY);

    scene.tweens.add({
        targets: cardSprite,
         x: endX,
         y: endY,
         scaleX: 0.25,
         scaleY: 0.25,
         duration: 500,
         ease: 'Linear',
    });
}

config = {
    type: Phaser.AUTO,
    width: 800,
    height: 600,
    transparent: true,
    scene: {
        preload: preload,
        create: create
    }
};

game = new Phaser.Game(config);


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
    var startGameButton = document.getElementById('startGameButton');
    var player1Session = document.getElementById('player1Session').innerText;
    var player2Session = document.getElementById('player2Session').innerText;

    startGameButton.remove();

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
    const playCardButton1 = document.getElementById('playCard1Button');
    const player1Session = document.getElementById('player1Session').innerText;
    const currentSession = localStorage.getItem("sessionId");

    const startX = playCardButton1.offsetLeft + playCardButton1.offsetWidth / 2;
    const startY = playCardButton1.offsetTop + playCardButton1.offsetHeight / 2;

    player1CardSprite.setTexture(`${card.name}_${card.suit}`);

    let endX, endY;
    if (player1Session === currentSession) {
        endX = 600;
        endY = 280; // the lower the number the higher the card
    } else {
        endX = 600;
        endY = 150;
    }


    playCardAnimation(game.scene.scenes[0],player1CardSprite,{ endX: endX, endY: endY, startX: startX, startY: startY });
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
    const playCardButton2 = document.getElementById('playCard2Button');
    const player2Session = document.getElementById('player2Session').innerText;
    const currentSession = localStorage.getItem("sessionId");

    const startX = playCardButton2.offsetLeft + playCardButton2.offsetWidth / 2;
    const startY = playCardButton2.offsetTop + playCardButton2.offsetHeight / 2;

    player2CardSprite.setTexture(`${card.name}_${card.suit}`);

    let endX, endY;
    if (player2Session === currentSession) {
        endX = 600;
        endY = 270;
    } else {
        endX = 600;
        endY = 150;
    }

    playCardAnimation(game.scene.scenes[0], player2CardSprite, { endX: endX, endY: endY, startX: startX, startY: startY });
}

//===========================================================DISPLAY CAPTURE CARDS===============================================

function displayPlayer1CapturedCards(capturedCards){
    const player1CapturedCards = document.getElementById('player1CapturedCards');
    player1CapturedCards.innerHTML = '';

    capturedCards.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.textContent = card.name + ' - ' + card.suit;
        player1CapturedCards.appendChild(cardElement);
    });

    if (capturedCards.length > 0) {
        player1CapturedCards.style.display = 'block';
    } else {
        player1CapturedCards.style.display = 'none';
    }

    displayPlayer1CapturedCardsCount(capturedCards.length);
}

function displayPlayer1CapturedCardsCount(count){
    const player1CapturedCardsCount = document.getElementById('player1CapturedCardsCount');
    player1CapturedCardsCount.textContent = "Captured cards count: " + count;
}

function displayPlayer2CapturedCards(capturedCards){

    const player2CapturedCards = document.getElementById('player2CapturedCards');
    player2CapturedCards.innerHTML = '';

    capturedCards.forEach(card => {
        const cardElement = document.createElement('div');
        cardElement.textContent = card.name + ' - ' + card.suit;
        player2CapturedCards.appendChild(cardElement);
    });

    if (capturedCards.length > 0) {
        player2CapturedCards.style.display = 'block';
    } else {
        player2CapturedCards.style.display = 'none';
    }

    displayPlayer2CapturedCardsCount(capturedCards.length);
}

function displayPlayer2CapturedCardsCount(count){
    const player2CapturedCardsCount = document.getElementById('player2CapturedCardsCount');
    player2CapturedCardsCount.textContent = "Captured cards count: " + count;
}


////===========================================================DEAL CAPTURED CARDS=================================================
//function dealCapturedCards1(cards) {
//    const player1CardsContainer = document.getElementById('player1Cards');
//    player1CardsContainer.innerHTML = '';
//    cards.forEach(card => {
//        const cardElement = document.createElement('div');
//        cardElement.textContent = card.name + ' - ' + card.suit;
//        player1CardsContainer.appendChild(cardElement);
//    });
//}
//
//function dealCapturedCards2(cards) {
//    const player1CardsContainer = document.getElementById('player2Cards');
//    player1CardsContainer.innerHTML = '';
//    cards.forEach(card => {
//        const cardElement = document.createElement('div');
//        cardElement.textContent = card.name + ' - ' + card.suit;
//        player1CardsContainer.appendChild(cardElement);
//    });
//}

//===========================================================WINNER MESSAGE=================================================

function displayWinner(winner){
     const winnerContainer = document.getElementById('winner');
     winnerContainer.textContent = "The winner is: " + winner;
}

function displayWarMessage(message){
     const messageContainer = document.getElementById('message');
     messageContainer.textContent =  message;
     setTimeout(() => {
        messageContainer.textContent = '';
     }, 3000);
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
    var currentSessionId = getSessionId();

    if (currentSessionId === gameState.player1Session) {
        if (gameState.game.player1 !== null) {
            alert("Player 2 has left the game.");
            if (document.getElementById('player2') != null) {
                document.getElementById('player2').innerText = "Player 2";
            }
        }
    } else if (currentSessionId === gameState.player2Session) {
        if (gameState.game.player2 !== null) {
            alert("Player 1 has left the game.");
            if (document.getElementById('player1') != null) {
                document.getElementById('player1').innerText = "Player 1";
            }
        }
    }
}
function adjustLayoutForPlayer(player2Session, currentSession) {

    if (player2Session === currentSession) {
       //Captured Cards
        var player1CapturedCards = document.getElementById('player1CapturedCards');
        var player2CapturedCards = document.getElementById('player2CapturedCards');

        player1CapturedCards.classList.add('captured-cards-player2');
        player2CapturedCards.classList.add('captured-cards-player1');

        //Captured Cards count
        var player1CapturedCardsCounts = document.getElementById('player1CapturedCardsCount');
        var player2CapturedCardsCounts = document.getElementById('player2CapturedCardsCount');

        player1CapturedCardsCounts.classList.add('Captured-Cards-Count2');
        player2CapturedCardsCounts.classList.add('Captured-Cards-Count1');

        //Play Card Button
        var player2PlayCard = document.getElementById('playCard2Button');
        player2PlayCard.classList.add('play-Card-1');

        //Play Usernames
        var player1UsernameSpan = document.querySelector('#player1 span');
        var player2UsernameSpan = document.querySelector('#player2 span');
        var tempText = player1UsernameSpan.textContent;
        player1UsernameSpan.textContent = player2UsernameSpan.textContent;
        player2UsernameSpan.textContent = tempText;
    }
}


//===========================================================PAGE BUTTONS=================================================

document.addEventListener('DOMContentLoaded', function() {
     var player2Session = document.getElementById('player2Session').innerText;
     var currentSession = localStorage.getItem("sessionId");

     console.log("inside player2Session",player2Session);
     console.log("inside currentSession",currentSession);
     adjustLayoutForPlayer(player2Session, currentSession);

   // Start game button
   var startGameButton = document.getElementById('startGameButton');
   if (startGameButton) {
       startGameButton.addEventListener('click', function(event) {
           event.preventDefault();
           var gameId = document.getElementById('gameID').textContent.trim();
           var player1Session = document.getElementById('player1Session').innerText;
           var player2Session = document.getElementById('player2Session').innerText;

           console.log("inside startGameButton");

           sendPlayer1DealCardsTest(player1Session);
           sendPlayer2DealCardsTest(player2Session);
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
             var gameIdText = document.getElementById('gameID').textContent.trim();
             var gameIdNumber = parseInt(gameIdText.split(' ')[2]);

             sendPlayer2PlayedCardMessageTest(player1Id,player2Id,gameIdNumber);
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

           var gameIdText = document.getElementById('gameID').textContent.trim();
           var gameIdNumber = parseInt(gameIdText.split(' ')[2]);

           if (player1Session === sessionId) {
               sendLeaveGameMessage(gameIdNumber, player1Session,player1Id);
               localStorage.removeItem('sessionId');
           } else {
               sendLeaveGameMessage(gameIdNumber, player2Session,player2Id);
               localStorage.removeItem('sessionId');
           }
           window.location.href = '/';
       });
   }
});
