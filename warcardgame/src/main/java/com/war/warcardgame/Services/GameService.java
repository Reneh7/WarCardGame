package com.war.warcardgame.Services;

import com.war.warcardgame.DTO.LeaveGameRequest;
import com.war.warcardgame.Enums.GameState;
import com.war.warcardgame.Models.GameEntity;
import com.war.warcardgame.Models.PlayersEntity;
import com.war.warcardgame.Repositories.GameRepository;
import com.war.warcardgame.Repositories.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GameService {
    private GameRepository gameRepository;
    private PlayerRepository playerRepository;
    private GameState gameState;

    public GameService(GameRepository gameRepository,PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
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

        Optional<GameEntity> optionalGame = gameRepository.findById(gameId);
        PlayersEntity player = playerRepository.findPlayerBySessionId(sessionId);

        if (optionalGame.isPresent() && player != null) {
            GameEntity game = optionalGame.get();

            if (player.equals(game.getPlayer1())) {
                game.setPlayer1(null);
            } else if (player.equals(game.getPlayer2())) {
                game.setPlayer2(null);
            }
            if (game.getPlayer1() == null || game.getPlayer2() == null) {
                game.setGameState(GameState.GAME_ENDED);
            }

            gameRepository.save(game);
            playerRepository.delete(player);
        }
    }
}
