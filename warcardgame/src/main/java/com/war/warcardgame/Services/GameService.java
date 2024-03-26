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

import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private GameState gameState;
    private CardsRepository cardsRepository;

    public GameService(GameRepository gameRepository,PlayerRepository playerRepository,CardsRepository cardsRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.cardsRepository = cardsRepository;
    }

    public GameEntity createNewGame(PlayersEntity player1){
         GameEntity newGame = new GameEntity(0,0,player1,null,null,gameState.NEW_GAME);
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
        Long playerId = request.getPlayerId();

        Optional<GameEntity> optionalGame = gameRepository.findById(gameId);
        PlayersEntity player = playerRepository.findPlayerBySessionId(sessionId);
        List<CardsEntity> cards = cardsRepository.findCardsByPlayers_PlayerId(playerId);

        if (optionalGame.isPresent() && player != null) {
            GameEntity game = optionalGame.get();

            if (player.equals(game.getPlayer1())) {
                game.setPlayer1(null);
                game.setWinner(game.getPlayer2());

            } else if (player.equals(game.getPlayer2())) {
                game.setPlayer2(null);
                game.setWinner(game.getPlayer1());
            }
            if (game.getPlayer1() == null || game.getPlayer2() == null) {
                game.setGameState(GameState.GAME_ENDED);
            }

            for (CardsEntity card : cards) {
                card.setPlayers(null);
                card.setPlayed(false);
            }

            gameRepository.save(game);
            cardsRepository.saveAll(cards);
            playerRepository.delete(player);
        }
    }
}
