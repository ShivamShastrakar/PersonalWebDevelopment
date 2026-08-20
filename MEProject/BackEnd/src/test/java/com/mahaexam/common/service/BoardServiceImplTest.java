package com.mahaexam.common.service;

import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.Board;
import com.mahaexam.common.repo.BoardRepository;
import com.mahaexam.common.repo.BoardStateMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BoardServiceImplTest {

    @Mock
    private BoardRepository boardRepository;

    @Mock
    private BoardStateMappingRepository boardStateMappingRepository;

    @InjectMocks
    private BoardServiceImpl boardService;

    private AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBoardsByTenant() {
        LocalDateTime now = LocalDateTime.now();

        List<Board> mockBoards = List.of(
                Board.builder()
                        .id(1)
                        .tenantId(1L)
                        .boardName("CBSE")
                        .createdAt(now)
                        .updatedAt(now)
                        .deleted("0")
                        .build(),

                Board.builder()
                        .id(2)
                        .tenantId(1L)
                        .boardName("ICSE")
                        .createdAt(now)
                        .updatedAt(now)
                        .deleted("0")
                        .build()
        );

        when(boardRepository.findAllByTenant(1L)).thenReturn(mockBoards);

        List<Board> result = boardService.getAllBoardsByTenant(1L);

        assertEquals(2, result.size());
        verify(boardRepository).findAllByTenant(1L);
    }

    @Test
    void testGetBoardById() {
        LocalDateTime now = LocalDateTime.now();

        Board board = Board.builder()
                .id(1)
                .tenantId(1L)
                .boardName("CBSE")
                .createdAt(now)
                .updatedAt(now)
                .deleted("0")
                .build();

        when(boardRepository.findById(1)).thenReturn(board);
        when(boardStateMappingRepository.findStateIdsByBoardId(1)).thenReturn(List.of(10, 20));

        Board result = boardService.getBoardById(1);

        assertEquals("CBSE", result.getBoardName());
        assertEquals(List.of(10, 20), result.getStateIds());
        verify(boardRepository).findById(1);
        verify(boardStateMappingRepository).findStateIdsByBoardId(1);
    }

    @Test
    void testCreateBoard_Success() {
        LocalDateTime now = LocalDateTime.now();

        Board newBoard = Board.builder()
                .tenantId(1L)
                .boardName("CBSE")
                .createdAt(now)
                .updatedAt(now)
                .deleted("0")
                .stateIds(List.of(10, 20))
                .build();

        when(boardRepository.existsByBoardNameAndTenantId("CBSE", 1L)).thenReturn(false);
        when(boardRepository.save(newBoard)).thenReturn(1);
        doNothing().when(boardStateMappingRepository).saveMappingsForBoard(1, List.of(10, 20));

        int id = boardService.createBoard(newBoard);

        assertEquals(1, id);
        verify(boardRepository).save(newBoard);
        verify(boardStateMappingRepository).saveMappingsForBoard(1, List.of(10, 20));
    }

    @Test
    void testCreateBoard_ThrowsValidationException() {
        Board newBoard = Board.builder()
                .tenantId(1L)
                .boardName("CBSE")
                .deleted("0")
                .build();

        when(boardRepository.existsByBoardNameAndTenantId("CBSE", 1L)).thenReturn(true);

        assertThrows(ValidationException.class, () -> boardService.createBoard(newBoard));
        verify(boardRepository, never()).save(any());
    }

    @Test
    void testUpdateBoard_Success() {
        Board board = Board.builder()
                .id(1)
                .tenantId(1L)
                .boardName("CBSE")
                .deleted("0")
                .stateIds(List.of(10, 20))
                .build();

        when(boardRepository.existsByBoardNameAndTenantIdExceptId("CBSE", 1L, 1)).thenReturn(false);
        when(boardRepository.update(board)).thenReturn(1);
        doNothing().when(boardStateMappingRepository).deleteMappingsForBoard(1);
        doNothing().when(boardStateMappingRepository).saveMappingsForBoard(1, List.of(10, 20));

        int result = boardService.updateBoard(board);

        assertEquals(1, result);
        verify(boardRepository).update(board);
        verify(boardStateMappingRepository).deleteMappingsForBoard(1);
        verify(boardStateMappingRepository).saveMappingsForBoard(1, List.of(10, 20));
    }

    @Test
    void testUpdateBoard_ThrowsValidationException() {
        Board board = Board.builder()
                .id(1)
                .tenantId(1L)
                .boardName("CBSE")
                .deleted("0")
                .build();

        when(boardRepository.existsByBoardNameAndTenantIdExceptId("CBSE", 1L, 1)).thenReturn(true);

        assertThrows(ValidationException.class, () -> boardService.updateBoard(board));
        verify(boardRepository, never()).update(any());
    }

    @Test
    void testDeleteBoard() {
        when(boardRepository.softDelete(1)).thenReturn(1);

        int result = boardService.deleteBoard(1);

        assertEquals(1, result);
        verify(boardRepository).softDelete(1);
    }
}
