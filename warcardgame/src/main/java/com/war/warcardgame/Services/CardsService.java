package com.war.warcardgame.Services;

import com.war.warcardgame.DTO.DealCardsResponse;
import com.war.warcardgame.Models.CardsEntity;
import com.war.warcardgame.Models.GameEntity;
import com.war.warcardgame.Models.PlayersEntity;
import com.war.warcardgame.Repositories.CardsRepository;
import com.war.warcardgame.Repositories.GameRepository;
import com.war.warcardgame.Repositories.PlayerRepository;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;


@Service
public class CardsService {
    private CardsRepository cardsRepository;
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private List<CardsEntity> allCards;

    public CardsService(CardsRepository cardsRepository, GameRepository gameRepository, PlayerRepository playerRepository) {
        this.cardsRepository = cardsRepository;
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.allCards = cardsRepository.findAll();
    }
    public DealCardsResponse dealPlayer1Cards(Long gameId) {
        GameEntity game = gameRepository.findById(gameId).orElse(null);
        PlayersEntity player1 = game.getPlayer1();
        String playerSession = player1.getSessionId();

        List<CardsEntity> player1Cards = allCards.subList(0, allCards.size() / 2);
        Collections.shuffle(player1Cards);

        assignCardsToPlayer(game.getPlayer1(), player1Cards);

        DealCardsResponse dealCardsResponse = new DealCardsResponse();
        dealCardsResponse.setCards(player1Cards);
        dealCardsResponse.setPlayerSession(playerSession);

        return dealCardsResponse;
    }

    public DealCardsResponse dealPlayer2Cards(Long gameId) {
        GameEntity game = gameRepository.findById(gameId).orElse(null);
        PlayersEntity player2 = game.getPlayer2();
        String playerSession = player2.getSessionId();

        List<CardsEntity> player2Cards = allCards.subList( allCards.size() / 2, allCards.size());
        Collections.shuffle(player2Cards);

        assignCardsToPlayer(game.getPlayer2(), player2Cards);

        DealCardsResponse dealCardsResponse = new DealCardsResponse();
        dealCardsResponse.setCards(player2Cards);
        dealCardsResponse.setPlayerSession(playerSession);

        return dealCardsResponse;
    }

    private void assignCardsToPlayer(PlayersEntity player, List<CardsEntity> cards) {
        for (CardsEntity card : cards) {
            card.setPlayers(player);
            cardsRepository.save(card);
        }
    }


    public CardsEntity player1PlayCard(long playerId){
        List<CardsEntity> player1Cards = cardsRepository.findCardsByPlayers_PlayerId(playerId);
        System.out.println("player1Cards in service "+ player1Cards);

        for (CardsEntity card : player1Cards) {
            if (!card.isPlayed()) {
                card.setPlayed(true);
                cardsRepository.save(card);
                System.out.println("inside player1PlayCard card:" + card);
                return card;
            }
        }
       return null;
    }

    public CardsEntity player2PlayCard(long playerId){
        List<CardsEntity> player2Cards = cardsRepository.findCardsByPlayers_PlayerId(playerId);
        System.out.println("player2Cards in service "+ player2Cards);

        for (CardsEntity card : player2Cards) {
            if (!card.isPlayed()) {
                card.setPlayed(true);
                cardsRepository.save(card);
                System.out.println("inside player2PlayCard card:" + card);
                return card;
            }
        }
        return null;
    }
}