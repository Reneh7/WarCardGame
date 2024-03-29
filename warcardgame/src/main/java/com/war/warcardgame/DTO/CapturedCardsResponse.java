package com.war.warcardgame.DTO;

import com.war.warcardgame.Models.CardsEntity;
import lombok.Data;

import java.util.List;

@Data
public class CapturedCardsResponse {
    private List<CardsEntity> player1CapturedCards;
    private List<CardsEntity> player2CapturedCards;
}
