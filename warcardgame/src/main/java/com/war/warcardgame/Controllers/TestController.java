//package com.war.warcardgame.Controllers;
//
//import com.war.warcardgame.DTO.PlayCard2Request;
//import com.war.warcardgame.DTO.DealCardsResponse;
//import com.war.warcardgame.DTO.PlayCardResponse;
//import com.war.warcardgame.Services.TestService;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class TestController {
//    TestService cardsService;
//
//
//    public TestController(TestService cardsService) {
//        this.cardsService = cardsService;
//    }
//
//    @MessageMapping("/cards/dealPlayer1Test")
//    @SendTo("/topic/dealCardsPlayer1Test")
//    public DealCardsResponse dealCardsPlayer1(String sessionId ) {
//        return cardsService.dealCards1(sessionId);
//    }
//
//    @MessageMapping("/cards/dealPlayer2Test")
//    @SendTo("/topic/dealCardsPlayer2Test")
//    public DealCardsResponse dealCardsPlayer2(String sessionId) {
//        return cardsService.dealCards2(sessionId);
//    }
//
////=================================================PLAY CARD====================================================
//
//    @MessageMapping("/cards/playCardPlayer1Test")
//    @SendTo("/topic/playCardPlayer1Test")
//    public PlayCardResponse playCardPlayer1(long playerId) {
//        return cardsService.playCard1(playerId);
//    }
//
//    @MessageMapping("/cards/playCardPlayer2Test")
//    @SendTo("/topic/playCardPlayer2Test")
//    public PlayCardResponse playCardPlayer2(PlayCard2Request playCard2Request) {
//        return cardsService.playCard2(playCard2Request);
//    }
//
//}
