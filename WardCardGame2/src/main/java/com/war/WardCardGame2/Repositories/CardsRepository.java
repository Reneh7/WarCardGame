package com.war.WardCardGame2.Repositories;

import com.war.WardCardGame2.Models.CardsEntity;
import com.war.WardCardGame2.Models.GameEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CardsRepository extends JpaRepository<CardsEntity,Long> {
}
