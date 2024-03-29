package com.war.warcardgame.Repositories;

import com.war.warcardgame.Models.CardsEntity;
import com.war.warcardgame.Models.PlayersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardsRepository extends JpaRepository<CardsEntity,Long> {
    List<CardsEntity> findCardsByPlayers_PlayerIdOrderByDealOrder(Long playerId);
}
