
//=====================================================CREATE NEW GAME AND PLAYER===============================================

function showUsernameForm() {
    document.getElementById('usernameForm').style.display = 'block';
}

function sendMessageCreate(username) {
 var sessionId = getSessionId();
    var message = {
        username: username,
        sessionId: sessionId
    };

    stompClient.send("/app/games/create", {}, JSON.stringify(message));
}

//===========================================REDIRECT TO GAME PAGE=================================================================

function redirectToGameplayPage(gameId) {
    window.location.href = "/gameplay?gameId=" + gameId;
}

function redirectJoinedPlayerToGameplayPage(gameId,joinedPlayerSessionId) {
    var sessionId = getSessionId();
    if (sessionId === joinedPlayerSessionId) {
        sendDealtCardsPlayer1Message(gameId);
        sendDealtCardsPlayer2Message(gameId);
    }
    window.location.href = "/gameplay?gameId=" + gameId;
}

//===========================================DEAL CARDS=================================================================

   function sendDealtCardsPlayer1Message(gameId) {
          stompClient.send("/app/cards/dealPlayer1", {}, gameId);
   }

      function sendDealtCardsPlayer2Message(gameId) {
          stompClient.send("/app/cards/dealPlayer2", {}, gameId);
   }

//===========================================UPDATE USERNAME UI========================================================
function updateUI(gameState) {
    document.getElementById('player1').innerText = gameState.player1Username;
    document.getElementById('player2').innerText = gameState.player2Username;
}
//=================================================JOIN GAME========================================================
function sendMessageJoin(gameId, username) {
    var sessionId = getSessionId();
    var message = {
        gameId: gameId,
        username: username,
        sessionId: sessionId
    };

    stompClient.send("/app/games/join", {}, JSON.stringify(message));
}

  function showGameIdForm() {
      document.getElementById('gameIdForm').style.display = 'block';
  }

//==============================================PAGE BUTTONS============================================================

document.addEventListener('DOMContentLoaded', function() {

    // Create game button
    var createGameButton = document.getElementById('createGameButton');
    if (createGameButton) {
        createGameButton.addEventListener('click', function(event) {
            event.preventDefault();
            showUsernameForm();
        });
    }

    // Submit username button
    var submitUsernameButton = document.getElementById('submitUsernameButton');
    if (submitUsernameButton) {
        submitUsernameButton.addEventListener('click', function(event) {
            event.preventDefault();
            var username = document.getElementById('usernameInput').value;
            sendMessageCreate(username);
        });
    }

    // Join game button
    var joinGameButton = document.getElementById('joinGameButton');
    if (joinGameButton) {
        joinGameButton.addEventListener('click', function(event) {
            event.preventDefault();
            showGameIdForm();
        });
    }

    // Submit game ID button
    var submitGameIdButton = document.getElementById('submitGameIdButton');
    if (submitGameIdButton) {
        submitGameIdButton.addEventListener('click', function(event) {
            event.preventDefault();
            var gameIdString = document.getElementById('gameIdInput').value;
            var gameId = parseInt(gameIdString);
            var username = document.getElementById('username2Input').value;
            sendMessageJoin(gameId, username);
        });
    }
});
//==========================================GENERATE SESSION ID=========================================================

function generateUUID() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}

function getSessionId() {
     var sessionId = localStorage.getItem('sessionId');
     if (!sessionId) {
            sessionId = generateUUID();
            localStorage.setItem('sessionId', sessionId);
     }
     return sessionId;
}

