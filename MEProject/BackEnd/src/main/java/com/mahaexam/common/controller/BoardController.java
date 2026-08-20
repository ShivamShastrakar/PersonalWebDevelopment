package com.mahaexam.common.controller;

import java.util.List;
import java.util.Objects;

import com.mahaexam.common.bean.SuccessResponseBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mahaexam.common.bean.BoardBean;
import com.mahaexam.common.bean.UserBean;
import com.mahaexam.common.model.Board;
import com.mahaexam.common.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/boards")
@Tag(name = "Board API", description = "Operations related to boards")
public class BoardController extends BaseController {

    private static final Logger logger = LogManager.getLogger(BoardController.class);

    @Autowired
    private BoardService service;

    @Operation(summary = "Create a new board")
    @PostMapping
    public ResponseEntity<SuccessResponseBean> create(@RequestBody BoardBean bean) {
        try{
            UserBean user = getUser();
            Board entity = new Board();
            entity.setBoardName(bean.getBoardName());
            entity.setTenantId(user.getTenantId());
            entity.setDeleted(bean.getDeleted());
            entity.setStateIds(bean.getStateIds());
            service.createBoard(entity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Board name registered successfully").build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Operation(summary = "Get all boards")
    @GetMapping
    public List<Board> getAll() {
    	UserBean user = getUser();
        return service.getAllBoardsByTenant(Objects.nonNull(user)? user.getTenantId().longValue():getCurrentTenantId());
    }

    @Operation(summary = "Get a board by ID")
    @GetMapping("/{id}")
    public Board getById(@PathVariable int id) {
        return service.getBoardById(id);
    }

    @Operation(summary = "Update a board")
    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponseBean> update(@PathVariable int id, @RequestBody BoardBean bean) {
        try{
            Board entity = new Board();
            entity.setId(id);
            entity.setBoardName(bean.getBoardName());
            entity.setTenantId(bean.getTenantId());
            entity.setDeleted(bean.getDeleted());
            entity.setStateIds(bean.getStateIds());
            service.updateBoard(entity);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(SuccessResponseBean.builder().status("success").message(
                            "Board name updated successfully" + entity.getId()).build());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    @Operation(summary = "Delete a board")
    @DeleteMapping("/{id}")
    public int delete(@PathVariable int id) {
        return service.deleteBoard(id);
    }

    @Operation(summary = "Get boards by state ID")
    @GetMapping("/state/{stateId}")
    public List<Board> getByStateId(@PathVariable Integer stateId) {
        UserBean user = getUser();
        Long tenantId = java.util.Objects.nonNull(user) ? user.getTenantId().longValue() : getCurrentTenantId();
        return service.getBoardsByStateId(stateId, tenantId);
    }
}