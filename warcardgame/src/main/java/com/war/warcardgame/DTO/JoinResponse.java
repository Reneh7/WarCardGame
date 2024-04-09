package com.war.warcardgame.DTO;

import lombok.Data;

@Data
public class JoinResponse {
    private Long gameId;
    private String joinedPlayerSessionId;
    private boolean isFound;
}
