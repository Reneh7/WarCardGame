package com.war.WardCardGame2.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "player_entity")
@Data
@NoArgsConstructor
public class PlayersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long playerId;
    @Column(name = "username")
    private String username;
    @OneToOne
    @JoinColumn(name = "game_id")
    private GameEntity gamePlayer;
    @OneToMany(mappedBy = "players")
    private Set<CardsEntity> playerCardsSet = new HashSet<>();

}
