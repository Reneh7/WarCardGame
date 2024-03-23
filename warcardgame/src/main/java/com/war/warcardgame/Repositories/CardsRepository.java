package com.war.warcardgame.Repositories;

import com.war.warcardgame.Models.CardsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardsRepository extends JpaRepository<CardsEntity,Long> {
    CardsEntity findByName(String name);
}
