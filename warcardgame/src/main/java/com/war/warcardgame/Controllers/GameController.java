package com.war.warcardgame.Controllers;

import com.war.warcardgame.DTO.*;
import com.war.warcardgame.Models.GameEntity;
import com.war.warcardgame.Models.PlayersEntity;
import com.war.warcardgame.Repositories.GameRepository;
import com.war.warcardgame.Repositories.PlayerRepository;
import com.war.warcardgame.Services.GameService;
import com.war.warcardgame.Services.PlayerService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Controller
public class GameController {
    private GameService gameService;
    private PlayerService playerService;
    private GameRepository gameRepository;
    private SimpMessagingTemplate messagingTemplate;
    private PlayerRepository playerRepository;

    public GameController(GameService gameService, PlayerService playerService,GameRepository gameRepository,SimpMessagingTemplate messagingTemplate, PlayerRepository playerRepository) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.gameRepository = gameRepository;
        this.messagingTemplate = messagingTemplate;
        this.playerRepository = playerRepository;
    }

    @GetMapping("/gameplay")
    public String gamePlayPage(Model model, @RequestParam(name = "gameId") Long gameId) {
        Optional<GameEntity> optionalGame = gameRepository.findById(gameId);
        int number = randomNumber();

        if (optionalGame.isPresent()) {
            GameEntity game = optionalGame.get();
            PlayersEntity player1 = game.getPlayer1();
            PlayersEntity player2 = game.getPlayer2();

            model.addAttribute("gameId", gameId);

            // UserName
            model.addAttribute("player1Username", player1 != null ? player1.getUsername() : null);
            model.addAttribute("player2Username", player2 != null ? player2.getUsername() : null);

            // Session
            model.addAttribute("player1Session", player1 != null ? player1.getSessionId() : null);
            model.addAttribute("player2Session", player2 != null ? player2.getSessionId(): null);


            // players id
            model.addAttribute("player1", player1.getPlayerId() != null ? player1.getPlayerId() : null);
            model.addAttribute("player2", player2 != null ? player2.getPlayerId() : null);

            // version number
            model.addAttribute("version", number );

            return "gamePlay";
        } else {
            return "gameNotFound";
        }
    }

    @MessageMapping("/games/create")
    @SendTo("/topic/createGame")
    public Map<String, String> createGameAndPlayer(CreateGameRequest request) {
        String sessionId = request.getSessionId();

        PlayersEntity player1 = playerService.createNewPlayer(request.getUsername(),sessionId);
        GameEntity newGame = gameService.createNewGame(player1);

        Map<String, String> response = new HashMap<>();
        response.put("gameId", newGame.getGameId().toString());
        response.put("createdPlayerSessionId", sessionId);
        return response;
    }

//    @MessageMapping("/games/join")
//    @SendTo("/topic/joinGame")
//    public JoinResponse joinGame(JoinGameRequest request) {
//        String sessionId = request.getSessionId();
//        JoinResponse response = new JoinResponse();
//
//        Optional<GameEntity> checkGame=gameRepository.findById(request.getGameId());
//
//        if (checkGame.isEmpty()) {
//            response.setJoinedPlayerSessionId(sessionId);
//            response.setFound(false);
//        } else {
//            PlayersEntity player2 = playerService.createNewPlayer(request.getUsername(),sessionId);
//            GameEntity game = gameService.joinGame(request.getGameId(), player2);
//            response.setFound(true);
//            response.setGameId(game.getGameId());
//            response.setJoinedPlayerSessionId(sessionId);
//        }
//        return response;
//    }

    @MessageMapping("/games/join")
    @SendTo("/topic/joinGame")
    public JoinResponse joinGame(JoinGameRequest request) {
        String sessionId = request.getSessionId();
        JoinResponse response = new JoinResponse();

        Optional<GameEntity> checkGame = gameRepository.findById(request.getGameId());

        if (checkGame.isPresent()) {
            PlayersEntity player2 = playerService.createNewPlayer(request.getUsername(), sessionId);
            GameEntity game = gameService.joinGame(request.getGameId(), player2);
            response.setFound(true);
            response.setGameId(game.getGameId());
            response.setJoinedPlayerSessionId(sessionId);
        } else {
            response.setJoinedPlayerSessionId(sessionId);
            response.setFound(false);
        }
        return response;
    }

    @MessageMapping("/games/leave")
    @SendTo("/topic/leaveGame")
    public void leaveGame(LeaveGameRequest request){
        gameService.leaveGame(request);

        Optional<GameEntity> game = gameRepository.findById(request.getGameId());
        LeaveGameResponse response = new LeaveGameResponse();
        if (game.isPresent()) {
            GameEntity gameEntity = game.get();
            PlayersEntity player1 = gameEntity.getPlayer1();
            PlayersEntity player2 = gameEntity.getPlayer2();
            String player1Session = player1 != null ? player1.getSessionId() : null;
            String player2Session = player2 != null ? player2.getSessionId() : null;

            response.setGame(gameEntity);
            response.setPlayer1Session(player1Session);
            response.setPlayer2Session(player2Session);
            messagingTemplate.convertAndSend("/topic/updateGameAfterLeave", response);
        }
    }

    private int randomNumber(){
        Random random = new Random();
        return random.nextInt(500);
    }

}
