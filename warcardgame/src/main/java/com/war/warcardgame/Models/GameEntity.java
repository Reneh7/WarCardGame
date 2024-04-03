package com.war.warcardgame.Models;

import com.war.warcardgame.Enums.GameState;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Table(name = "game_entity")
@Data
@NoArgsConstructor
public class GameEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;
    @ManyToOne
    @JoinColumn(name = "player1_id")
    private PlayersEntity player1;
    @ManyToOne
    @JoinColumn(name = "player2_id")
    private PlayersEntity player2;
    @ManyToOne
    @JoinColumn(name = "winner_id")
    private PlayersEntity winner;
    @Enumerated(EnumType.STRING)
    @Column(name = "game_state")
    private GameState gameState;

    public GameEntity(PlayersEntity player1, PlayersEntity player2, PlayersEntity winner, GameState gameState) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
        this.gameState = gameState;
    }
}
