package com.war.WardCardGame2.Controllers;

import com.war.WardCardGame2.Models.CardsEntity;
import com.war.WardCardGame2.Services.CardsService;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@Controller
public class CardWebSocketsController  {
    CardsService cardsService;

    public CardWebSocketsController(CardsService cardsService) {
        this.cardsService = cardsService;
    }

    @GetMapping("/test")
    public String testWebSocketPage() {
        System.out.println("Returning Test.html");
        return "test.html";
    }

    @GetMapping("/app/{gameId}/shuffle")
    @SendTo("/topic/shuffle/{gameId}")
    public String  shuffleDeck(@PathVariable long gameId) {
        System.out.println("Inside controller");
        List<CardsEntity> shuffledCards = cardsService.shuffleCards(gameId);
        return "Hello";
//        List<CardsEntity> shuffledCards = cardsService.shuffleCards(gameId);
//        System.out.println("shuffler" + shuffledCards);
//        return shuffledCards;
    }

//    @MessageMapping("/{gameId}/players/{playerId}/deal")
//    @SendTo("/topic/game/{gameId}")
//    public Map<PlayersEntity, List<CardsEntity>> dealCards(@PathVariable long gameId, @PathVariable long player1Id, @PathVariable long player2Id) {
//        return cardsService.dealCards(gameId, player1Id, player2Id);
//    }

}
