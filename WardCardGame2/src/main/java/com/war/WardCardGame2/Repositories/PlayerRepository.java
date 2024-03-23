package com.war.WardCardGame2.Repositories;

import com.war.WardCardGame2.Models.PlayersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayersEntity,Long> {

}
