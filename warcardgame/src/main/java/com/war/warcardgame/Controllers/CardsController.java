package com.war.warcardgame.Controllers;

import com.war.warcardgame.DTO.DealCardsResponse;
import com.war.warcardgame.DTO.PlayCardRequest;
import com.war.warcardgame.DTO.PlayCardResponse;
import com.war.warcardgame.Services.CardsService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class CardsController {
    //Changed
    CardsService cardsService;


    public CardsController(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @MessageMapping("/cards/dealPlayer1Test")
    @SendTo("/topic/dealCardsPlayer1Test")
    public DealCardsResponse dealCardsPlayer1(String sessionId ) {
        return cardsService.dealCards1(sessionId);
    }

    @MessageMapping("/cards/dealPlayer2Test")
    @SendTo("/topic/dealCardsPlayer2Test")
    public DealCardsResponse dealCardsPlayer2(String sessionId) {
        return cardsService.dealCards2(sessionId);
    }

//=================================================PLAY CARD====================================================

    @MessageMapping("/cards/playCardPlayer1Test")
    @SendTo("/topic/playCardPlayer1Test")
    public PlayCardResponse playCardPlayer1(long player1Id) {
        return cardsService.playCard1(player1Id);
    }

    @MessageMapping("/cards/playCardPlayer2Test")
    @SendTo("/topic/playCardPlayer2Test")
    public PlayCardResponse playCardPlayer2(PlayCardRequest playCardRequest) {
        return cardsService.playCard2(playCardRequest);
    }

}
