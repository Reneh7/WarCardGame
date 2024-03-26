package com.war.warcardgame.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "card_entity")
@Data
@NoArgsConstructor
public class CardsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;
    @Column(name = "name")
    private String name;
    @Column(name = "suit")
    private String suit;
    @Column(name = "rank")
    private int rank;

    @Column(name = "is_played")
    private boolean isPlayed;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayersEntity players;
}
