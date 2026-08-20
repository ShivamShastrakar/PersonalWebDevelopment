package com.mahaexam.common.service;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.stream.Collectors;

import com.mahaexam.common.exception.ValidationException;
import org.springframework.stereotype.Service;
import com.mahaexam.common.model.Board;
import com.mahaexam.common.repo.BoardRepository;
import com.mahaexam.common.repo.BoardStateMappingRepository;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final BoardStateMappingRepository boardStateMappingRepository;

    public BoardServiceImpl(BoardRepository boardRepository, BoardStateMappingRepository boardStateMappingRepository) {
        this.boardRepository = boardRepository;
        this.boardStateMappingRepository = boardStateMappingRepository;
    }

    @Override
    public List<Board> getAllBoardsByTenant(Long tenantId) {
        List<Board> boards = boardRepository.findAllByTenant(tenantId);
        if (boards != null && !boards.isEmpty()) {
            List<Integer> boardIds = boards.stream().map(Board::getId).collect(Collectors.toList());
            Map<Integer, List<Integer>> stateMap = boardStateMappingRepository.findStateIdsForBoards(boardIds);
            for (Board board : boards) {
                board.setStateIds(stateMap.getOrDefault(board.getId(), new ArrayList<>()));
            }
        }
        return boards;
    }

    @Override
    public Board getBoardById(int id) {
        Board board = boardRepository.findById(id);
        if (board != null) {
            List<Integer> stateIds = boardStateMappingRepository.findStateIdsByBoardId(board.getId());
            board.setStateIds(stateIds);
        }
        return board;
    }

    @Override
    public int createBoard(Board board) {
        if (boardRepository.existsByBoardNameAndTenantId(board.getBoardName(), board.getTenantId())) {
            throw new ValidationException("Board name already exists for this tenant.");
        }
        int boardId = boardRepository.save(board);
        boardStateMappingRepository.saveMappingsForBoard(boardId, board.getStateIds());
        return boardId;
    }

    @Override
    public int updateBoard(Board board) {
        if (boardRepository.existsByBoardNameAndTenantIdExceptId(board.getBoardName(), board.getTenantId(), board.getId())) {
            throw new ValidationException("Board name already exists for this tenant.");
        }
        int result = boardRepository.update(board);
        boardStateMappingRepository.deleteMappingsForBoard(board.getId());
        boardStateMappingRepository.saveMappingsForBoard(board.getId(), board.getStateIds());
        return result;
    }

    @Override
    public int deleteBoard(int id) {
        return boardRepository.softDelete(id);
    }

    @Override
    public List<Board> getBoardsByStateId(Integer stateId, Long tenantId) {
        List<Board> allBoards = this.getAllBoardsByTenant(tenantId);
        if (allBoards == null || allBoards.isEmpty()) {
            return new ArrayList<>();
        }

        return allBoards.stream().filter(board -> {
            List<Integer> boardStates = board.getStateIds();
            // Show board if it's for everyone (no mappings) or specifically for this state
            return boardStates == null || boardStates.isEmpty() || (stateId != null && boardStates.contains(stateId));
        }).collect(Collectors.toList());
    }
}