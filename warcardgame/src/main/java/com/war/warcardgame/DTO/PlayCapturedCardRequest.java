package com.war.warcardgame.DTO;

import lombok.Data;

@Data
public class PlayCapturedCardRequest {
    private long playerId;
    private long gameId;
}
