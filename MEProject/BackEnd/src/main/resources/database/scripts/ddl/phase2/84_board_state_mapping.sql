--liquibase formatted sql
--changeset mahaexam:add-board-state-mapping-table

CREATE TABLE board_state_mapping (
    id SERIAL PRIMARY KEY,
    board_id INTEGER NOT NULL,
    state_id INTEGER NOT NULL,
    CONSTRAINT fk_board FOREIGN KEY (board_id) REFERENCES board(id),
    CONSTRAINT fk_state FOREIGN KEY (state_id) REFERENCES state(id)
);

CREATE INDEX idx_board_state_board_id ON board_state_mapping(board_id);
CREATE INDEX idx_board_state_state_id ON board_state_mapping(state_id);

