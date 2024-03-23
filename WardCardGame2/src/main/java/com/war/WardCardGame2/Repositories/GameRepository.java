package com.war.WardCardGame2.Repositories;

import com.war.WardCardGame2.Models.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GameRepository extends JpaRepository<GameEntity,Long> {

}
