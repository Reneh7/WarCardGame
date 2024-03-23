package com.war.warcardgame.Services;

import com.war.warcardgame.Models.CardsEntity;
import com.war.warcardgame.Models.GameEntity;
import com.war.warcardgame.Models.PlayersEntity;
import com.war.warcardgame.Repositories.CardsRepository;
import com.war.warcardgame.Repositories.GameRepository;
import com.war.warcardgame.Repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CardsService {
    CardsRepository cardsRepository;
    GameRepository gameRepository;
    PlayerRepository playerRepository;


    public CardsService(CardsRepository cardsRepository, GameRepository gameRepository, PlayerRepository playerRepository) {
        this.cardsRepository = cardsRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
    }

    public List<CardsEntity> shuffleCards(Long gameId) {
        GameEntity game = gameRepository.findById(gameId).orElse(null);
        if (game == null) {
            return Collections.emptyList();
        }

        List<CardsEntity> allCards = cardsRepository.findAll();
        List<CardsEntity> shuffledCards = new ArrayList<>();

        for (CardsEntity card : allCards) {
            shuffledCards.add(card);
        }

        Collections.shuffle(shuffledCards);
        return shuffledCards;
    }
    public List<CardsEntity> dealPlayer1Cards(Long gameId) {
        System.out.println("Inside dealPlayer1Cards service");
        GameEntity game = gameRepository.findById(gameId).orElse(null);
        List<CardsEntity> allCards = shuffleCards(gameId);

        List<CardsEntity> player1Cards = allCards.subList(0, allCards.size() / 2);

        assignCardsToPlayer(game.getPlayer1(), player1Cards);
        System.out.println(player1Cards);
        return player1Cards;
    }

    public List<CardsEntity> dealPlayer2Cards(Long gameId) {
        System.out.println("Inside dealPlayer2Cards service");
        GameEntity game = gameRepository.findById(gameId).orElse(null);
        List<CardsEntity> allCards = shuffleCards(gameId);

        List<CardsEntity> player2Cards = allCards.subList( allCards.size() / 2, allCards.size());

        assignCardsToPlayer(game.getPlayer2(), player2Cards);
        System.out.println(player2Cards);
        return player2Cards;
    }

    private void assignCardsToPlayer(PlayersEntity player, List<CardsEntity> cards) {
        for (CardsEntity card : cards) {
            card.setPlayers(player);
        }
    }

}