//package com.war.warcardgame.Controllers;
//
//import com.war.warcardgame.DTO.DealCardsResponse;
//import com.war.warcardgame.Models.CardsEntity;
//import com.war.warcardgame.Services.CardsService;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//
//@Controller
//public class CardsController {
//    CardsService cardsService;
//
//
//    public CardsController(CardsService cardsService) {
//        this.cardsService = cardsService;
//    }
//
//    @MessageMapping("/cards/dealPlayer1")
//    @SendTo("/topic/dealCardsPlayer1")
//    public DealCardsResponse dealCardsPlayer1(long gameId) {
//        return cardsService.dealPlayer1Cards(gameId);
//    }
//
//    @MessageMapping("/cards/dealPlayer2")
//    @SendTo("/topic/dealCardsPlayer2")
//    public DealCardsResponse  dealCardsPlayer2(long gameId) {
//        return cardsService.dealPlayer2Cards(gameId);
//    }
//
////=================================================PLAY CARD====================================================
//
//    @MessageMapping("/cards/playCardPlayer1")
//    @SendTo("/topic/playCardPlayer1")
//    public CardsEntity playCardPlayer1(long playerId) {
//        return cardsService.playCard1(playerId);
//    }
//
//    @MessageMapping("/cards/playCardPlayer2")
//    @SendTo("/topic/playCardPlayer2")
//    public CardsEntity playCardPlayer2(long playerId) {
//        return cardsService.playCard2(playerId);
//    }

//=================================================CARD CAPTURING====================================================

//    @MessageMapping("/cards/capturedCards")
//    @SendTo("/topic/capturedCards")
//    public CapturedCardsResponse CapturedCards(CapturedCardsRequest capturedCardsRequest) {
//        System.out.println("5 Inside CapturedCards controller");
//
//        long player1Id = capturedCardsRequest.getPlayer1Id();
//        long player2Id = capturedCardsRequest.getPlayer2Id();
//
//        return cardsService.gameRound(player1Id,player2Id);
//    }


//}