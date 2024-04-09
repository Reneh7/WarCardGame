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

//===========================================REDIRECT TO GAME PAGE=======================================================

function redirectToGameplayPage(gameId) {
    window.location.href = "/gameplay?gameId=" + gameId;
}

//============================================VALIDATIONS================================================================

function validateUsername(username) {
    return username.trim() !== '';
}

function validateGameId(gameIdString) {
    return gameIdString.trim() !== '';
}

function displayErrorMessage(message) {
        alert(message);
}

//==============================================PAGE BUTTONS============================================================

document.addEventListener('DOMContentLoaded', function() {

    var usernameForm = document.getElementById('usernameForm');
    var gameIdForm = document.getElementById('gameIdForm');
    var closeBtns = document.querySelectorAll('.close');

    function openModal(modal) {
        modal.style.display = 'block';
    }

    function closeModal(modal) {
        modal.style.display = 'none';
    }

    // Create game button
    var createGameButton = document.getElementById('createGameButton');
    if (createGameButton) {
        createGameButton.addEventListener('click', function(event) {
            event.preventDefault();

            openModal(usernameModal);
            showUsernameForm();

        });
    }

    // Join game button
    var joinGameButton = document.getElementById('joinGameButton');
    if (joinGameButton) {
        joinGameButton.addEventListener('click', function(event) {
            event.preventDefault();
            openModal(gameIdModal);
            showGameIdForm();
        });
    }

    // Rules button
    var rulesButton = document.getElementById('instructions');
    if (rulesButton) {
        rulesButton.addEventListener('click', function(event) {
            event.preventDefault();
            openModal(instructionsIdModal);
        });
    }

    // Close modal when clicking on the close button
    closeBtns.forEach(function(btn) {
        btn.addEventListener('click', function(event) {
            event.preventDefault();
            var modal = btn.closest('.modal');
            closeModal(modal);
        });
    });

    // Close modal when clicking outside the modal
    window.onclick = function(event) {
        if (event.target.classList.contains('modal')) {
            closeModal(event.target);
        }
    };

    // Submit username button
    var submitUsernameButton = document.getElementById('submitUsernameButton');
    if (submitUsernameButton) {
        submitUsernameButton.addEventListener('click', function(event) {
            event.preventDefault();
            var username = document.getElementById('usernameInput').value;

            if (validateUsername(username)) {
                sendMessageCreate(username);
                closeModal(usernameModal);
            } else {
                displayErrorMessage("Please enter a username.");
            }
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

            if(validateUsername(username) && validateGameId(gameIdString)){
                sendMessageJoin(gameId, username);
                closeModal(gameIdModal);
            } else if (!validateUsername(username)) {
                displayErrorMessage("Please enter a username.");
            } else {
                displayErrorMessage("Please enter game id.");
            }
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

