package com.war.warcardgame.Controllers;

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
    public List<CardsEntity> dealCardsPlayer1(long gameId) {
        System.out.println("Inside dealPlayer1Cards controller");
        List<CardsEntity> dealCards = cardsService.dealPlayer1Cards(gameId);
        return dealCards;
    }

    @MessageMapping("/cards/dealPlayer2")
    @SendTo("/topic/dealCardsPlayer2")
    public List<CardsEntity> dealCardsPlayer2(long gameId) {
        System.out.println("Inside dealPlayer2Cards controller");
        List<CardsEntity> dealCards = cardsService.dealPlayer2Cards(gameId);
        return dealCards;
    }
}