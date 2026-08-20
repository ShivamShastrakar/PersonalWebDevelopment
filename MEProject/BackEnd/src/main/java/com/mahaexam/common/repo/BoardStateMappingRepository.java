package com.mahaexam.common.repo;

import java.util.List;

public interface BoardStateMappingRepository {
    void saveMappingsForBoard(Integer boardId, List<Integer> stateIds);
    void deleteMappingsForBoard(Integer boardId);
    List<Integer> findStateIdsByBoardId(Integer boardId);
    List<Integer> findBoardIdsByStateId(Integer stateId);
    java.util.Map<Integer, List<Integer>> findStateIdsForBoards(List<Integer> boardIds);
}

