CREATE TABLE player_entity (
    player_id bigserial PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    session_id VARCHAR(64)
);

CREATE TABLE card_entity (
    card_id bigserial PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    suit VARCHAR(10) NOT NULL,
    rank INT NOT NULL,
    is_played Boolean NOT NULL,
    deal_order INT NOT NULL,
    player_id BIGINT REFERENCES player_entity(player_id)
);

CREATE TABLE game_entity (
    game_id bigserial PRIMARY KEY,
    player1_id BIGINT,
    player2_id BIGINT,
    winner_id BIGINT,
    game_state VARCHAR(50),
    FOREIGN KEY (player1_id) REFERENCES player_entity(player_id),
    FOREIGN KEY (player2_id) REFERENCES player_entity(player_id),
    FOREIGN KEY (winner_id) REFERENCES player_entity(player_id)
);


