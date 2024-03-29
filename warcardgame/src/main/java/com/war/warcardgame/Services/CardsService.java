//package com.war.warcardgame.Services;
//
//import com.war.warcardgame.DTO.CapturedCardsResponse;
//import com.war.warcardgame.DTO.DealCardsResponse;
//import com.war.warcardgame.DTO.PlayerTurnResponse;
//import com.war.warcardgame.Models.CardsEntity;
//import com.war.warcardgame.Models.GameEntity;
//import com.war.warcardgame.Models.PlayersEntity;
//import com.war.warcardgame.Repositories.CardsRepository;
//import com.war.warcardgame.Repositories.GameRepository;
//import com.war.warcardgame.Repositories.PlayerRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//
//@Service
//public class CardsService {
//    private final CardsRepository cardsRepository;
//    private final GameRepository gameRepository;
//    private final PlayerRepository playerRepository;
//    private List<CardsEntity> allCards;
//    private boolean player1Turn = true;
//    private boolean player2Turn = false;
//
//    public CardsService(CardsRepository cardsRepository, GameRepository gameRepository, PlayerRepository playerRepository) {
//        this.cardsRepository = cardsRepository;
//        this.gameRepository = gameRepository;
//        this.playerRepository = playerRepository;
//    }
//
////===========================================================DEAL CARDS=====================================================================
//
//    public DealCardsResponse dealPlayer1Cards(Long gameId) {
//        allCards = new ArrayList<>();
//        allCards = cardsRepository.findAll();
//        GameEntity game = gameRepository.findById(gameId).orElse(null);
//
//        if (game == null) {
//            throw new RuntimeException("Game not found");
//        }
//
//        PlayersEntity player1 = game.getPlayer1();
//        String playerSession = player1.getSessionId();
//
//        List<CardsEntity> player1Cards = allCards.subList(0, allCards.size() / 2);
//        Collections.shuffle(player1Cards);
//
//        assignCardsToPlayer(player1 , player1Cards);
//
//        DealCardsResponse dealCardsResponse = new DealCardsResponse();
//        dealCardsResponse.setPlayerSession(playerSession);
//        dealCardsResponse.setCards(player1Cards);
//
//        return dealCardsResponse;
//    }
//
//    public DealCardsResponse dealPlayer2Cards(Long gameId) {
//        allCards = new ArrayList<>();
//        allCards = cardsRepository.findAll();
//        GameEntity game = gameRepository.findById(gameId).orElse(null);
//
//        if (game == null) {
//            throw new RuntimeException("Game not found");
//        }
//
//        PlayersEntity player2 = game.getPlayer2();
//        String playerSession = player2.getSessionId();
//
//        List<CardsEntity> player2Cards = allCards.subList( allCards.size() / 2, allCards.size());
//        Collections.shuffle(player2Cards);
//
//        assignCardsToPlayer(game.getPlayer2(), player2Cards);
//
//        DealCardsResponse dealCardsResponse = new DealCardsResponse();
//        dealCardsResponse.setCards(player2Cards);
//        dealCardsResponse.setPlayerSession(playerSession);
//
//        return dealCardsResponse;
//    }
//
//    private void assignCardsToPlayer(PlayersEntity player, List<CardsEntity> cards) {
//        for (CardsEntity card : cards) {
//            card.setPlayers(player);
//            cardsRepository.save(card);
//        }
//    }
//
////==========================================================PLAY CARDS=====================================================================
//    public PlayerTurnResponse player1PlayCard(long playerId){
//        List<CardsEntity> player1Cards = cardsRepository.findCardsByPlayers_PlayerId(playerId);
//        System.out.println("player1PlayCard player1Cards: " + player1Cards);
//
//        PlayerTurnResponse playerTurnResponse = new PlayerTurnResponse();
//        Optional<PlayersEntity> optionalPlayer1 = playerRepository.findById(playerId);
//        PlayersEntity player1 = optionalPlayer1.get();
//
//        if (player1Turn){
//            for (CardsEntity card : player1Cards) {
//                if (!card.isPlayed()) {
//                    System.out.println("card player 1 inside if:" + card);
//                    card.setPlayed(true);
//                    playerTurnResponse.setCard(card);
//                    playerTurnResponse.setTurn(true);
//
//                    player1Turn = false;
//                    player2Turn = true;
//                    cardsRepository.save(card);
//                    return playerTurnResponse;
//                }
//            }
//        } else {
//            playerTurnResponse.setTurn(false);
//            playerTurnResponse.setPlayerSession(player1.getSessionId());
//            CardsEntity lastCard = playerTurnResponse.getCard();
//            System.out.println("8 lastCard in player1" + lastCard);
//            playerTurnResponse.setCard(lastCard);
//            return playerTurnResponse;
//        }
//        return null;
//    }
//
//    public PlayerTurnResponse player2PlayCard(long playerId){
//        List<CardsEntity> player2Cards = cardsRepository.findCardsByPlayers_PlayerId(playerId);
//        System.out.println("player2PlayCard player2Cards: " + player2Cards);
//
//        PlayerTurnResponse playerTurnResponse = new PlayerTurnResponse();
//        Optional<PlayersEntity> optionalPlayer2 = playerRepository.findById(playerId);
//        PlayersEntity player2 = optionalPlayer2.get();
//
//
//        if (player2Turn) {
//            for (CardsEntity card : player2Cards) {
//                if (!card.isPlayed()) {
//                    System.out.println("card player 2 inside if:" + card);
//                    card.setPlayed(true);
//                    playerTurnResponse.setCard(card);
//                    playerTurnResponse.setTurn(true);
//
//                    player2Turn = false;
//                    player1Turn = true;
//                    cardsRepository.save(card);
//                    return playerTurnResponse;
//                }
//            }
//        } else {
//            playerTurnResponse.setTurn(false);
//            playerTurnResponse.setPlayerSession(player2.getSessionId());
//            CardsEntity lastCard = playerTurnResponse.getCard();
//            System.out.println("9 lastCard in player2" + lastCard);
//            playerTurnResponse.setCard(lastCard);
//            return playerTurnResponse;
//        }
//        return null;
//    }
//
//
//
//    public CardsEntity playCard1(long playerId){
//        List<CardsEntity> player1Cards = cardsRepository.findCardsByPlayers_PlayerId(playerId);
//
//        for (CardsEntity card : player1Cards) {
//            if (!card.isPlayed()) {
//                card.setPlayed(true);
//                cardsRepository.save(card);
//                return card;
//            }
//        }
//        return null;
//    }
//
//    public CardsEntity playCard2(long playerId){
//        List<CardsEntity> player2Cards = cardsRepository.findCardsByPlayers_PlayerId(playerId);
//        for (CardsEntity card : player2Cards) {
//            if (!card.isPlayed()) {
//                card.setPlayed(true);
//                cardsRepository.save(card);
//                return card;
//            }
//        }
//        return null;
//    }
//
//
//
//
////===========================================================CARD CAPTURING=====================================================================
//
//    public CapturedCardsResponse gameRound(long player1Id , long player2Id){
//        System.out.println("6 player1Turn in gameRound: " + player1Turn);
//        System.out.println("7 player2Turn in gameRound: " + player2Turn);
//
//
//        PlayerTurnResponse player1 = player1PlayCard(player1Id);
//        PlayerTurnResponse player2 = player2PlayCard(player2Id);
//        CapturedCardsResponse capturedCardsResponse = new CapturedCardsResponse();
//
//        CardsEntity player1Card = player1.getCard();
//        CardsEntity player2Card = player2.getCard();
//
//        System.out.println("Inside gameRound player1Card: " + player1Card);
//        System.out.println("Inside gameRound player2Card: " + player2Card);
//
//        if(player1Card.getRank() > player2Card.getRank()){
//            capturedCardsResponse.setPlayer1CapturedCards(List.of(player1Card, player2Card));
//            System.out.println("capturedCardsResponse first if in gameRound" + capturedCardsResponse);
//            return capturedCardsResponse;
//        } else if (player1Card.getRank() < player2Card.getRank()){
//            capturedCardsResponse.setPlayer2CapturedCards(List.of(player2Card, player1Card));
//            System.out.println("capturedCardsResponse second if in gameRound" + capturedCardsResponse);
//            return capturedCardsResponse;
//        } else {
//           return war(player1Id, player2Id);
//        }
//    }
//
//    public CapturedCardsResponse war(long player1Id , long player2Id){
//        List<CardsEntity> player1CapturedCards = new ArrayList<>();
//        List<CardsEntity> player2CapturedCards = new ArrayList<>();
//        CapturedCardsResponse capturedCardsResponse = new CapturedCardsResponse();
//
//        for (int i = 0; i < 3; i++) {
//            player1PlayCard(player1Id);
//            player2PlayCard(player2Id);
//        }
//
//        PlayerTurnResponse player1 = player1PlayCard(player1Id);
//        PlayerTurnResponse player2 = player2PlayCard(player2Id);
//        CardsEntity player1Card = player1.getCard();
//        CardsEntity player2Card = player2.getCard();
//
//        if(player1Card.getRank() > player2Card.getRank()){
//            player1CapturedCards.add(player1Card);
//            player1CapturedCards.add(player2Card);
//            capturedCardsResponse.setPlayer1CapturedCards(player1CapturedCards);
//            System.out.println("capturedCardsResponse first if in war" + capturedCardsResponse);
//            return capturedCardsResponse;
//        } else if (player1Card.getRank() < player2Card.getRank()){
//            player2CapturedCards.add(player1Card);
//            player2CapturedCards.add(player2Card);
//            capturedCardsResponse.setPlayer2CapturedCards(player2CapturedCards);
//            System.out.println("capturedCardsResponse second if in war" + capturedCardsResponse);
//            return capturedCardsResponse;
//
//        } else {
//            return war(player1Id, player2Id);
//        }
//    }
//}