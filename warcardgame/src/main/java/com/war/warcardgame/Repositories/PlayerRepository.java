package com.war.warcardgame.Repositories;

import com.war.warcardgame.Models.PlayersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayersEntity,Long> {
    PlayersEntity findPlayerBySessionId(String sessionId);
}