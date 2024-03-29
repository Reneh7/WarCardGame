package com.war.warcardgame.Services;

import com.war.warcardgame.DTO.DealCardsResponse;
import com.war.warcardgame.DTO.PlayCardResponse;
import com.war.warcardgame.Models.CardsEntity;
import com.war.warcardgame.Models.PlayersEntity;
import com.war.warcardgame.Repositories.CardsRepository;
import com.war.warcardgame.Repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CardsService2 {
    private final CardsRepository cardsRepository;
    private final PlayerRepository playerRepository;
    private List<CardsEntity> allCards;
    private Boolean player1Turn = true;
    private Boolean player2Turn = false;

    public CardsService2(CardsRepository cardsRepository, PlayerRepository playerRepository) {
        this.cardsRepository = cardsRepository;
        this.playerRepository = playerRepository;
    }


    public DealCardsResponse dealCards1(String sessionId) {
        allCards = new ArrayList<>();
        allCards = cardsRepository.findAll();

        PlayersEntity player1 = playerRepository.findPlayerBySessionId(sessionId);
        List<CardsEntity> player1Cards = allCards.subList(0, allCards.size() / 2);
        Collections.shuffle(player1Cards);

        for (int i = 0; i < player1Cards.size(); i++) {
            CardsEntity card = player1Cards.get(i);
            card.setDealOrder(i);
            card.setPlayers(player1);
            cardsRepository.save(card);
        }

        DealCardsResponse dealCardsResponse = new DealCardsResponse();
        dealCardsResponse.setPlayerSession(sessionId);
        dealCardsResponse.setCards(player1Cards);

        return dealCardsResponse;
    }

    public DealCardsResponse dealCards2(String sessionId) {
        allCards = new ArrayList<>();
        allCards = cardsRepository.findAll();

        PlayersEntity player2 = playerRepository.findPlayerBySessionId(sessionId);
        List<CardsEntity> player2Cards = allCards.subList( allCards.size() / 2, allCards.size());
        Collections.shuffle(player2Cards);

        for (int i = 0; i < player2Cards.size(); i++) {
            CardsEntity card = player2Cards.get(i);
            card.setDealOrder(i);
            card.setPlayers(player2);
            cardsRepository.save(card);
        }

        DealCardsResponse dealCardsResponse = new DealCardsResponse();
        dealCardsResponse.setPlayerSession(sessionId);
        dealCardsResponse.setCards(player2Cards);

        return dealCardsResponse;
    }


    public PlayCardResponse playCard1(long playerId){
        List<CardsEntity> player1Cards = cardsRepository.findCardsByPlayers_PlayerIdOrderByDealOrder(playerId);
        Optional<PlayersEntity> optionalPlayer1 = playerRepository.findById(playerId);
        PlayersEntity player1 = optionalPlayer1.get();
        PlayCardResponse playCardResponse = new PlayCardResponse();

        if(player1Turn){
            for (CardsEntity card : player1Cards) {
                if (!card.isPlayed()) {
                    card.setPlayed(true);
                    cardsRepository.save(card);

                    playCardResponse.setCard(card);
                    playCardResponse.setTurn(true);
                    playCardResponse.setPlayerSession(player1.getSessionId());

                    player1Turn = false;
                    player2Turn = true;

                    return playCardResponse;
                }
            }
        } else {
            playCardResponse.setTurn(false);
            playCardResponse.setPlayerSession(player1.getSessionId());
            return playCardResponse;
        }
        return null;
    }

    public PlayCardResponse playCard2(long playerId){
        List<CardsEntity> player2Cards = cardsRepository.findCardsByPlayers_PlayerIdOrderByDealOrder(playerId);
        Optional<PlayersEntity> optionalPlayer2 = playerRepository.findById(playerId);
        PlayersEntity player2 = optionalPlayer2.get();
        PlayCardResponse playCardResponse = new PlayCardResponse();


        if(player2Turn) {
            for (CardsEntity card : player2Cards) {
                if (!card.isPlayed()) {
                    card.setPlayed(true);
                    cardsRepository.save(card);

                    playCardResponse.setCard(card);
                    playCardResponse.setTurn(player2Turn);
                    playCardResponse.setPlayerSession(player2.getSessionId());

                    player2Turn = false;
                    player1Turn = true;

                    return playCardResponse;
                }
            }
        } else {
            playCardResponse.setTurn(false);
            playCardResponse.setPlayerSession(player2.getSessionId());
            return playCardResponse;
        }

        return null;
    }

    public void resetTurn(){
        player1Turn = true;
        player2Turn = false;
    }
}