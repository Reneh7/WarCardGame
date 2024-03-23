package com.war.warcardgame.Services;

import com.war.warcardgame.Models.PlayersEntity;
import com.war.warcardgame.Repositories.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayersEntity createNewPlayer(String username,String sessionId){
        PlayersEntity newPlayer = new PlayersEntity();
        newPlayer.setUsername(username);
        newPlayer.setSessionId(sessionId);
        playerRepository.save(newPlayer);
        return newPlayer;
    }
}
