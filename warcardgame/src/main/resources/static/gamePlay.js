document.getElementById('leaveGameButton').addEventListener('click', function(event) {
    event.preventDefault();

    var sessionId = localStorage.getItem("sessionId");
    var player1Session = document.getElementById('player1Session').innerText;
    var player2Session = document.getElementById('player2Session').innerText;

    var gameId = document.getElementById('gameID').innerText;

    if (player1Session === sessionId) {
        sendLeaveGameMessage(gameId, player1Session);
    } else {
        sendLeaveGameMessage(gameId, player2Session);
    }


    document.getElementById('player1').innerText = "Player 1";
    document.getElementById('player2').innerText = "Player 2";

    window.location.href = '/';
});

function sendLeaveGameMessage(gameId, playerSession) {
    var message = {
        gameId: gameId,
        playerSession: playerSession
    };
    stompClient.send("/app/games/leave", {}, JSON.stringify(message));
}


