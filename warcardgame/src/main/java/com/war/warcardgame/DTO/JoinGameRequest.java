package com.war.warcardgame.DTO;

import lombok.Data;

@Data
public class JoinGameRequest {
    private Long gameId;
    private String username;
    private String sessionId;
}
