package com.war.WardCardGame2.Controllers;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller

public class GameWebSocketsController {
    @MessageMapping("/joinGame/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public String joinGame(String message){
        return "Join game";
    }
    @MessageMapping("/startGame/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public String startGame(String message){
        return "Game started";
    }
    @MessageMapping("/retrieveGameState/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public String retrieveGameState(String message){
        return "Current game state";
    }
    @MessageMapping("/endGame/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public String endGame(String message){
        return "Game ended";
    }

    @MessageMapping("/declareWinner/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public String declareWinner(String message) {
        return "Winner declared";
    }
}
