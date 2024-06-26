package com.war.warcardgame.Services;

import com.war.warcardgame.DTO.LeaveGameRequest;
import com.war.warcardgame.Enums.GameState;
import com.war.warcardgame.Models.CardsEntity;
import com.war.warcardgame.Models.GameEntity;
import com.war.warcardgame.Models.PlayersEntity;
import com.war.warcardgame.Repositories.CardsRepository;
import com.war.warcardgame.Repositories.GameRepository;
import com.war.warcardgame.Repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private GameState gameState;
    private final CardsRepository cardsRepository;
    private final CardsService cardsService;


    public GameService(GameRepository gameRepository, PlayerRepository playerRepository, CardsRepository cardsRepository, CardsService cardsService) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.cardsRepository = cardsRepository;
        this.cardsService = cardsService;
    }

    public GameEntity createNewGame(PlayersEntity player1){
         GameEntity newGame = new GameEntity(player1,null,null, GameState.NEW_GAME);
         gameRepository.save(newGame);
         return newGame;
    }

    public GameEntity joinGame(Long gameId,PlayersEntity player2){

        Optional<GameEntity> optionalGame = gameRepository.findById(gameId);
        if (optionalGame.isPresent()) {
            GameEntity game = optionalGame.get();

            if (game.getPlayer1() != null && game.getPlayer2() == null) {
                game.setPlayer2(player2);
                gameRepository.save(game);
                return game;
            }


        }
        return null;
    }

    public void leaveGame(LeaveGameRequest request){
        Long gameId = request.getGameId();
        String sessionId = request.getPlayerSession();

        Optional<GameEntity> optionalGame = gameRepository.findById(gameId);

        PlayersEntity player = playerRepository.findPlayerBySessionId(sessionId);

        if (optionalGame.isPresent() && player != null) {
            GameEntity game = optionalGame.get();

            if (player.equals(game.getPlayer1())) {
                game.setPlayer1(null);
                game.setWinner(game.getPlayer2());
                reDealCards(game, game.getPlayer1(), game.getPlayer2());
            } else if (player.equals(game.getPlayer2())) {
                game.setPlayer2(null);
                game.setWinner(game.getPlayer1());
                reDealCards(game, game.getPlayer1(), game.getPlayer2());
            }
            if (game.getPlayer1() == null && game.getPlayer2() == null) {
                gameRepository.delete(game);
            } else {
                game.setGameState(GameState.GAME_ENDED);
                gameRepository.save(game);
            }

            playerRepository.delete(player);
            cardsService.resetTurn();
            cardsService.resetCapturedCards();
        }
    }

    private void reDealCards(GameEntity game, PlayersEntity player1, PlayersEntity player2) {
        List<CardsEntity> allCards = cardsRepository.findAll();

        for (CardsEntity card : allCards) {
            card.setPlayers(null);
            card.setPlayed(false);
        }

        Collections.shuffle(allCards);

        List<CardsEntity> player1Cards = allCards.subList(0, allCards.size() / 2);
        List<CardsEntity> player2Cards = allCards.subList(allCards.size() / 2, allCards.size());

        dealCards(player1, player1Cards);
        dealCards(player2, player2Cards);
    }

    private void dealCards(PlayersEntity player, List<CardsEntity> cards) {
        for (int i = 0; i < cards.size(); i++) {
            CardsEntity card = cards.get(i);
            card.setDealOrder(i);
            card.setPlayers(player);
            cardsRepository.save(card);
        }
    }



}
