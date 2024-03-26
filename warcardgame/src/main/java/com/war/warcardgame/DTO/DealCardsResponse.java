package com.war.warcardgame.DTO;

import com.war.warcardgame.Models.CardsEntity;
import lombok.Data;

import java.util.List;

@Data
public class DealCardsResponse {
    private List<CardsEntity> cards;
    private String playerSession;
}
