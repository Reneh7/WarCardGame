package com.war.WardCardGame2.Models;

import com.war.WardCardGame2.Enums.GameState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "game_entity")
@Data
@NoArgsConstructor
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;
    @Column(name = "player1_score")
    private int player1Score;
    @Column(name = "player2_score")
    private int player2Score;
    @ManyToOne
    @JoinColumn(name = "player1_id")
    private PlayersEntity player1;
    @ManyToOne
    @JoinColumn(name = "player2_id")
    private PlayersEntity player2;
    @OneToOne
    @JoinColumn(name = "winner_id")
    private PlayersEntity winner;
    @OneToMany(mappedBy = "gameCards")
    private Set<CardsEntity> cards = new HashSet<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "game_state")
    private GameState gameState;
}
