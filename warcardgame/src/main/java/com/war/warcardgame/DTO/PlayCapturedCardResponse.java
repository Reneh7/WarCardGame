package com.war.warcardgame.DTO;

import com.war.warcardgame.Models.CardsEntity;
import lombok.Data;

@Data
public class PlayCapturedCardResponse {
    private CardsEntity card;
    private boolean turn;
    private String playerSession;

}
