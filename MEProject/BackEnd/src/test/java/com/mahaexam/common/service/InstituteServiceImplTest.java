package com.mahaexam.common.service;

import com.mahaexam.common.bean.InstituteBean;
import com.mahaexam.common.bean.KeywordSearchRequest;
import com.mahaexam.common.exception.ValidationException;
import com.mahaexam.common.model.Institute;
import com.mahaexam.common.repo.InstituteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InstituteServiceImplTest {

    @Mock
    private InstituteRepository repository;

    @InjectMocks
    private InstituteServiceImpl service;

    private InstituteBean bean;
    private Institute institute;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bean = InstituteBean.builder()
                .instituteName("Test Institute")
                .indexNumber("123")
                .tokenId("abc")
                .createdAt(LocalDateTime.now())
                .build();

        institute = Institute.builder()
                .id(1)
                .instituteName("Test Institute")
                .indexNumber("123")
                .tokenId("abc")
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void testCreateInstitute_Success() {
        when(repository.existsByInstituteName("Test Institute")).thenReturn(false);
        when(repository.save(any())).thenReturn(1);

        int result = service.createInstitute(bean);

        assertEquals(1, result);
        verify(repository).save(any(Institute.class));
    }

    @Test
    void testCreateInstitute_Duplicate_ThrowsException() {
        when(repository.existsByInstituteName("Test Institute")).thenReturn(true);

        assertThrows(ValidationException.class, () -> service.createInstitute(bean));
        verify(repository, never()).save(any());
    }

    @Test
    void testUpdateInstitute_Success() {
        when(repository.existsByInstituteNameExceptId("Test Institute", 1)).thenReturn(false);
        when(repository.update(any())).thenReturn(1);

        int result = service.updateInstitute(1, bean);

        assertEquals(1, result);
        verify(repository).update(any(Institute.class));
    }

    @Test
    void testUpdateInstitute_Duplicate_ThrowsException() {
        when(repository.existsByInstituteNameExceptId("Test Institute", 1)).thenReturn(true);

        assertThrows(ValidationException.class, () -> service.updateInstitute(1, bean));
        verify(repository, never()).update(any());
    }

    @Test
    void testGetInstituteById() {
        when(repository.findById(1)).thenReturn(institute);

        Institute result = service.getInstituteById(1);

        assertEquals("Test Institute", result.getInstituteName());
        verify(repository).findById(1);
    }

    @Test
    void testGetAllInstitutes() {
        when(repository.findAll()).thenReturn(Arrays.asList(institute));

        List<Institute> result = service.getAllInstitutes();

        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void testDeleteInstitute() {
        when(repository.delete(1)).thenReturn(1);

        int result = service.deleteInstitute(1);

        assertEquals(1, result);
        verify(repository).delete(1);
    }

    @Test
    void testSearchByKeywords_Found() {
        KeywordSearchRequest request = KeywordSearchRequest.builder()
                .keyword1("123")
                .keyword2("456")
                .keyword3("789")
                .build();

        String expectedIndexNumber = "123.456.789";

        when(repository.searchByIndexNumber(expectedIndexNumber)).thenReturn(Optional.of(institute));

        InstituteBean result = service.searchByKeywords(request);

        assertNotNull(result);
        assertEquals("Test Institute", result.getInstituteName());
        verify(repository).searchByIndexNumber(expectedIndexNumber);
    }

    @Test
    void testSearchByKeywords_NotFound() {
        KeywordSearchRequest request = KeywordSearchRequest.builder()
                .keyword1("000")
                .keyword2("111")
                .keyword3("222")
                .build();

        when(repository.searchByIndexNumber("000.111.222")).thenReturn(Optional.empty());

        InstituteBean result = service.searchByKeywords(request);

        assertNull(result);
    }
}