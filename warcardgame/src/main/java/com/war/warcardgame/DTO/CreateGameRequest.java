package com.war.warcardgame.DTO;

import lombok.Data;

@Data
public class CreateGameRequest {
    private String username;
    private String sessionId;
}
