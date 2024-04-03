package com.war.warcardgame.DTO;

import lombok.Data;

@Data
public class PlayCardRequest {
    private Long player1Id;
    private Long player2Id;
    private Long gameId;
    public PlayCardRequest(Long player1Id, Long player2Id,Long gameId) {
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.gameId = gameId;
    }
}
