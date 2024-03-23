package com.war.warcardgame.Repositories;

import com.war.warcardgame.Models.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameEntity,Long> {

}