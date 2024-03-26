package com.war.warcardgame.DTO;

import lombok.Data;

@Data
public class LeaveGameRequest {
    private Long gameId;
    private String playerSession;
    private Long playerId;
}
