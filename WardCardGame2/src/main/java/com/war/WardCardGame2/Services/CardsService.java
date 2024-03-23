package com.war.WardCardGame2.Services;

import com.war.WardCardGame2.Models.*;
import com.war.WardCardGame2.Repositories.*;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class CardsService {
    CardsRepository cardsRepository;
    GameRepository gameRepository;
    PlayerRepository playerRepository;


    public CardsService(CardsRepository cardsRepository,GameRepository gameRepository,PlayerRepository playerRepository){
        this.cardsRepository = cardsRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }
    public List<CardsEntity> shuffleCards(Long gameId) {
        System.out.println("Inside service");
        GameEntity game = gameRepository.findById(gameId).orElse(null);
        List<CardsEntity> staticCardsList = Arrays.asList(new CardsEntity());
        return staticCardsList;
    }

//    public List<CardsEntity> shuffleCards(Long gameId) {
//        System.out.println("Inside service");
//        GameEntity game = gameRepository.findById(gameId).orElse(null);
//        if (game == null) {
//            return Collections.emptyList();
//        }
//
//        List<CardsEntity> allCards = cardsRepository.findAll();
//        List<CardsEntity> shuffledCards = new ArrayList<>();
//
//        for (CardsEntity card : allCards) {
//            shuffledCards.add(card);
//        }
//
//        Collections.shuffle(shuffledCards);
//        System.out.println("Shuffled cards: " + shuffledCards);
//
//        return shuffledCards;
//    }


//    public Map<PlayersEntity, List<CardsEntity>>  dealCards(Long gameId, Long playerId1, Long playerId2){
//        GameEntity game = gameRepository.findById(gameId).orElse(null);
//        PlayersEntity player1 = playerRepository.findById(playerId1).orElse(null);
//        PlayersEntity player2 = playerRepository.findById(playerId2).orElse(null);
//
//        if (game == null || player1 == null || player2 == null){
//            return Collections.emptyMap();
//        }
//
//        List<CardsEntity> shuffledCards = shuffleCards(gameId);
//
//        List<CardsEntity> dealCardsPlayer1 = shuffledCards.subList(0, 26);
//        List<CardsEntity> dealCardsPlayer2 = shuffledCards.subList(26, 52);
//
//        for (CardsEntity card : dealCardsPlayer1) {
//            PlayerCardsEntity playerCard = new PlayerCardsEntity();
//            playerCard.setPlayer(player1);
//            playerCard.setCard(card);
//            playerCardsRepository.save(playerCard);
//        }
//
//        for (CardsEntity card : dealCardsPlayer2) {
//            PlayerCardsEntity playerCard = new PlayerCardsEntity();
//            playerCard.setPlayer(player2);
//            playerCard.setCard(card);
//            playerCardsRepository.save(playerCard);
//        }
//
//        Map<PlayersEntity, List<CardsEntity>> dealtCards = new HashMap<>();
//        dealtCards.put(player1, dealCardsPlayer1);
//        dealtCards.put(player2, dealCardsPlayer2);
//        return dealtCards;
//    }
}
