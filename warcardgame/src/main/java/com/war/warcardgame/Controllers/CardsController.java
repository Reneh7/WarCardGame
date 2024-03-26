package com.war.warcardgame.Controllers;

import com.war.warcardgame.DTO.DealCardsResponse;
import com.war.warcardgame.Models.CardsEntity;
import com.war.warcardgame.Services.CardsService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


import java.util.List;


@Controller
public class CardsController {
    CardsService cardsService;


    public CardsController(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @MessageMapping("/cards/dealPlayer1")
    @SendTo("/topic/dealCardsPlayer1")
    public DealCardsResponse dealCardsPlayer1(long gameId) {
        System.out.println("Inside dealPlayer1Cards controller");

        return cardsService.dealPlayer1Cards(gameId);
    }

    @MessageMapping("/cards/dealPlayer2")
    @SendTo("/topic/dealCardsPlayer2")
    public DealCardsResponse dealCardsPlayer2(long gameId) {
        System.out.println("Inside dealPlayer2Cards controller");
        return cardsService.dealPlayer2Cards(gameId);
    }

//=================================================PLAY CARD====================================================

    @MessageMapping("/cards/playCardPlayer1")
    @SendTo("/topic/playCardPlayer1")
    public CardsEntity playCardPlayer1(long playerId) {
        System.out.println("Inside playCardPlayer1 controller");
        return cardsService.player1PlayCard(playerId);
    }

    @MessageMapping("/cards/playCardPlayer2")
    @SendTo("/topic/playCardPlayer2")
    public CardsEntity playCardPlayer2(long playerId) {
        System.out.println("Inside playCardPlayer2 controller");
        return cardsService.player2PlayCard(playerId);
    }
}