package com.war.warcardgame.Controllers;

import com.war.warcardgame.DTO.DealCardsResponse;
import com.war.warcardgame.DTO.PlayCardResponse;
import com.war.warcardgame.Models.CardsEntity;
import com.war.warcardgame.Services.CardsService2;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class CardsController2 {
    CardsService2 cardsService2;


    public CardsController2(CardsService2 cardsService2) {
        this.cardsService2 = cardsService2;
    }

    @MessageMapping("/cards/dealPlayer1Test")
    @SendTo("/topic/dealCardsPlayer1Test")
    public DealCardsResponse dealCardsPlayer1(String sessionId ) {
        return cardsService2.dealCards1(sessionId);
    }

    @MessageMapping("/cards/dealPlayer2Test")
    @SendTo("/topic/dealCardsPlayer2Test")
    public DealCardsResponse dealCardsPlayer2(String sessionId) {
        return cardsService2.dealCards2(sessionId);
    }

//=================================================PLAY CARD====================================================

    @MessageMapping("/cards/playCardPlayer1Test")
    @SendTo("/topic/playCardPlayer1Test")
    public PlayCardResponse playCardPlayer1(long playerId) {
        return cardsService2.playCard1(playerId);
    }

    @MessageMapping("/cards/playCardPlayer2Test")
    @SendTo("/topic/playCardPlayer2Test")
    public PlayCardResponse playCardPlayer2(long playerId) {
        return cardsService2.playCard2(playerId);
    }

}
