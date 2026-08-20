package com.mahaexam.common.repo;

import java.util.List;

import com.mahaexam.common.model.Board;

public interface BoardRepository {
    List<Board> findAllByTenant(Long tenantId);
    Board findById(int id);
    int save(Board board);
    int update(Board board);
    int softDelete(int id);
    boolean existsByBoardNameAndTenantId(String boardName, Long tenantId);
    boolean existsByBoardNameAndTenantIdExceptId(String boardName, Long tenantId, int excludeId);
    List<Board> findAllByIds(List<Integer> ids);
}
