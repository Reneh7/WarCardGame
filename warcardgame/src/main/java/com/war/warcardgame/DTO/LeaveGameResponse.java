package com.war.warcardgame.DTO;

import com.war.warcardgame.Models.GameEntity;
import lombok.Data;

@Data
public class LeaveGameResponse {
    private GameEntity game;
    private String player1Session;
    private String player2Session;
}
