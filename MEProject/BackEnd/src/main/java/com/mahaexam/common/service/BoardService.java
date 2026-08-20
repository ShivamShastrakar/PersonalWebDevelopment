package com.mahaexam.common.service;

import java.util.List;

import com.mahaexam.common.model.Board;

public interface BoardService {
    List<Board> getAllBoardsByTenant(Long tenantId);
    Board getBoardById(int id);
    int createBoard(Board board);
    int updateBoard(Board board);
    int deleteBoard(int id);
    List<Board> getBoardsByStateId(Integer stateId, Long tenantId);
}