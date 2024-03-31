//package com.war.warcardgame.Services;
//
//import com.war.warcardgame.DTO.PlayCard2Request;
//import com.war.warcardgame.DTO.CapturedCardsResponse;
//import com.war.warcardgame.DTO.DealCardsResponse;
//import com.war.warcardgame.DTO.PlayCardResponse;
//import com.war.warcardgame.Models.CardsEntity;
//import com.war.warcardgame.Models.PlayersEntity;
//import com.war.warcardgame.Repositories.CardsRepository;
//import com.war.warcardgame.Repositories.PlayerRepository;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class TestService {
//    private final CardsRepository cardsRepository;
//    private final PlayerRepository playerRepository;
//    private List<CardsEntity> allCards;
//    private Boolean player1Turn = true;
//    private Boolean player2Turn = false;
//    private CardsEntity playedCard1;
//    private CardsEntity playedCard2;
//    private List<CardsEntity> player1CapturedCards = new ArrayList<>();
//    private List<CardsEntity> player2CapturedCards = new ArrayList<>();
//    private final SimpMessagingTemplate messagingTemplate;
//
//    public TestService(CardsRepository cardsRepository, PlayerRepository playerRepository,SimpMessagingTemplate messagingTemplate) {
//        this.cardsRepository = cardsRepository;
//        this.playerRepository = playerRepository;
//        player1CapturedCards = new ArrayList<>();
//        player2CapturedCards = new ArrayList<>();
//        this.messagingTemplate = messagingTemplate;
//    }
//
////==================================================DEALING======================================================
//
//    public DealCardsResponse dealCards1(String sessionId) {
//        allCards = new ArrayList<>();
//        allCards = cardsRepository.findAll();
//
//        PlayersEntity player1 = playerRepository.findPlayerBySessionId(sessionId);
//        List<CardsEntity> player1Cards = allCards.subList(0, allCards.size() / 2);
//        Collections.shuffle(player1Cards);
//
//        for (int i = 0; i < player1Cards.size(); i++) {
//            CardsEntity card = player1Cards.get(i);
//            card.setDealOrder(i);
//            card.setPlayers(player1);
//            cardsRepository.save(card);
//        }
//
//        DealCardsResponse dealCardsResponse = new DealCardsResponse();
//        dealCardsResponse.setPlayerSession(sessionId);
//        dealCardsResponse.setCards(player1Cards);
//
//        return dealCardsResponse;
//    }
//
//    public DealCardsResponse dealCards2(String sessionId) {
//        allCards = new ArrayList<>();
//        allCards = cardsRepository.findAll();
//
//        PlayersEntity player2 = playerRepository.findPlayerBySessionId(sessionId);
//        List<CardsEntity> player2Cards = allCards.subList( allCards.size() / 2, allCards.size());
//        Collections.shuffle(player2Cards);
//
//        for (int i = 0; i < player2Cards.size(); i++) {
//            CardsEntity card = player2Cards.get(i);
//            card.setDealOrder(i);
//            card.setPlayers(player2);
//            cardsRepository.save(card);
//        }
//
//        DealCardsResponse dealCardsResponse = new DealCardsResponse();
//        dealCardsResponse.setPlayerSession(sessionId);
//        dealCardsResponse.setCards(player2Cards);
//
//        return dealCardsResponse;
//    }
//
////==================================================PLAYING CARDS===================================================
//
//    public PlayCardResponse playCard1(long playerId){
//        List<CardsEntity> player1Cards = cardsRepository.findCardsByPlayers_PlayerIdOrderByDealOrder(playerId);
//        Optional<PlayersEntity> optionalPlayer1 = playerRepository.findById(playerId);
//        PlayersEntity player1 = optionalPlayer1.get();
//        PlayCardResponse playCardResponse = new PlayCardResponse();
//
//        if(player1Turn){
//            for (CardsEntity card : player1Cards) {
//                if (!card.isPlayed()) {
//                    card.setPlayed(true);
//                    cardsRepository.save(card);
//
//                    playCardResponse.setCard(card);
//                    playCardResponse.setTurn(true);
//                    playCardResponse.setPlayerSession(player1.getSessionId());
//
//                    playedCard1 = card;
//                    player1Turn = false;
//                    player2Turn = true;
//
//                    return playCardResponse;
//                }
//            }
//        } else {
//            playCardResponse.setTurn(false);
//            playCardResponse.setPlayerSession(player1.getSessionId());
//            return playCardResponse;
//        }
//        return null;
//    }
//
//    public PlayCardResponse playCard2(PlayCard2Request playCard2Request){
//        long player1Id = playCard2Request.getPlayer1Id();
//        long player2Id = playCard2Request.getPlayer2Id();
//        List<CardsEntity> player2Cards = cardsRepository.findCardsByPlayers_PlayerIdOrderByDealOrder(player2Id);
//        Optional<PlayersEntity> optionalPlayer2 = playerRepository.findById(player2Id);
//        PlayersEntity player2 = optionalPlayer2.get();
//        PlayCardResponse playCardResponse = new PlayCardResponse();
//
//
//        if(player2Turn) {
//            for (CardsEntity card : player2Cards) {
//                if (!card.isPlayed()) {
//                    card.setPlayed(true);
//                    cardsRepository.save(card);
//
//                    playCardResponse.setCard(card);
//                    playCardResponse.setTurn(player2Turn);
//                    playCardResponse.setPlayerSession(player2.getSessionId());
//
//                    System.out.println("card in play2 in playCard2: " + card );
//                    playedCard2 = card;
//                    System.out.println("playedCard2 in playCard2: " + playedCard2 );
//
//                    gameRound(player1Id,player2Id);
//
//                    player2Turn = false;
//                    player1Turn = true;
//
//                    return playCardResponse;
//                }
//            }
//        } else {
//            playCardResponse.setTurn(false);
//            playCardResponse.setPlayerSession(player2.getSessionId());
//            return playCardResponse;
//        }
//
//        return null;
//    }
//
//    public void resetTurn(){
//        player1Turn = true;
//        player2Turn = false;
//    }
//
////==============================================CARD CAPTURING==================================================
//
//    public CapturedCardsResponse gameRound(long player1Id, long player2Id) {
//        CapturedCardsResponse capturedCardsResponse = new CapturedCardsResponse();
//
//        System.out.println("playedCard1 in gameRound:" + playedCard1);
//        System.out.println("playedCard2 in gameRound:" + playedCard2);
//        System.out.println("player1Turn in gameRound:" + player1Turn);
//        System.out.println("player2Turn in gameRound:" + player2Turn);
//
//        if (playedCard1.getRank() > playedCard2.getRank()) {
//            System.out.println("Inside first if in capture");
//
//            player1CapturedCards.add(playedCard1);
//            player1CapturedCards.add(playedCard2);
//
//            capturedCardsResponse.setPlayer1CapturedCards(player1CapturedCards);
//
//            messagingTemplate.convertAndSend("/topic/capturedCards/player1", capturedCardsResponse.getPlayer1CapturedCards());
//        } else if (playedCard1.getRank() < playedCard2.getRank()) {
//            System.out.println("Inside second if in capture");
//
//            player2CapturedCards.add(playedCard1);
//            player2CapturedCards.add(playedCard2);
//
//            capturedCardsResponse.setPlayer2CapturedCards(player2CapturedCards);
//
//            messagingTemplate.convertAndSend("/topic/capturedCards/player2", capturedCardsResponse.getPlayer2CapturedCards());
//        } else {
//            System.out.println("Inside third if in capture");
//            PlayCard2Request playCard2Request = new PlayCard2Request();
//            playCard2Request.setPlayer1Id(player1Id);
//            playCard2Request.setPlayer2Id(player2Id);
//
//            war(playCard2Request);
//        }
//        System.out.println("outside if in capture");
//        return capturedCardsResponse;
//    }
//
//
//    public List<CardsEntity> war(PlayCard2Request playCard2Request){
//        long player1Id = playCard2Request.getPlayer1Id();
//        long player2Id = playCard2Request.getPlayer2Id();
//        PlayCardResponse player1Response = new PlayCardResponse();
//        PlayCardResponse player2Response = new PlayCardResponse();
//        int i = 0;
//
//        while (i < 4){
//            player1Response = playCard1(player1Id);
//            player2Response = playCard2(playCard2Request);
//            i++;
//        }
//
//        CardsEntity player1Card =  player1Response.getCard();
//        CardsEntity player2Card = player2Response.getCard();
//        if (player1Card.getRank() > player2Card.getRank()){
//            player1CapturedCards.add(playedCard1);
//            player1CapturedCards.add(playedCard2);
//        } else if(player1Card.getRank() < player2Card.getRank()){
//            player2CapturedCards.add(playedCard1);
//            player2CapturedCards.add(playedCard2);
//        } else {
//            war(playCard2Request);
//        }
//        return null;
//    }
//
//    public void resetCapturedCards(){
//        player1CapturedCards = new ArrayList<>();
//        player2CapturedCards = new ArrayList<>();
//    }
//
//
//}