package com.war.warcardgame.DTO;

import lombok.Data;

@Data
public class CapturedCardsRequest {
    private Long player1Id;
    private Long player2Id;
}
