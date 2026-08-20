package com.mahaexam.common.service;

import com.mahaexam.common.model.Config;
import com.mahaexam.common.repo.ConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConfigServiceImplTest {

    @Mock
    private ConfigRepository repository;

    @InjectMocks
    private ConfigServiceImpl configService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave_NewConfig_Success() {
        Config config = Config.builder()
                .name("enableFeature")
                .value("1")
                .build();

        when(repository.save(any(Config.class))).thenReturn(config);

        Config saved = configService.save(config);

        assertNotNull(saved);
        assertEquals("enableFeature", saved.getName());
        verify(repository, times(1)).save(any(Config.class));
    }

    @Test
    void testSave_NullConfig_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            configService.save(null);
        });
        assertEquals("Configuration cannot be null", ex.getMessage());
    }

    @Test
    void testFindByName_ValidName_ReturnsConfig() {
        Config config = Config.builder().name("maxAttempts").value("5").build();
        when(repository.findByName("maxAttempts")).thenReturn(Optional.of(config));

        Optional<Config> result = configService.findByName("maxAttempts");

        assertTrue(result.isPresent());
        assertEquals("5", result.get().getValue());
    }

    @Test
    void testFindByName_EmptyName_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            configService.findByName("  ");
        });
        assertEquals("Configuration name is required", ex.getMessage());
    }

    @Test
    void testGetBinaryBooleanConfig_ValidTrue() {
        Config config = Config.builder().name("enabled").value("1").build();
        when(repository.findByName("enabled")).thenReturn(Optional.of(config));

        Boolean result = configService.getBinaryBooleanConfig("enabled");

        assertTrue(result);
    }

    @Test
    void testGetBinaryBooleanConfig_InvalidValue_ThrowsException() {
        Config config = Config.builder().name("enabled").value("yes").build();
        when(repository.findByName("enabled")).thenReturn(Optional.of(config));

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            configService.getBinaryBooleanConfig("enabled");
        });
        assertTrue(ex.getMessage().contains("Invalid binary boolean value"));
    }

    @Test
    void testFindAllActive_ReturnsList() {
        List<Config> configs = Arrays.asList(
                Config.builder().name("one").value("1").build()
        );
        when(repository.findAllActive()).thenReturn(configs);

        List<Config> result = configService.findAllActive();

        assertEquals(1, result.size());
    }

    @Test
    void testUpdate_ExistingConfig_Success() {
        Config config = Config.builder()
                .name("retryCount")
                .value("3")
                .build();

        when(repository.findByName("retryCount")).thenReturn(Optional.of(config));
        when(repository.update(any(Config.class))).thenReturn(config);

        Config updated = configService.update(config);

        assertNotNull(updated);
        verify(repository).update(any(Config.class));
    }

    @Test
    void testUpdate_ConfigNotFound_ThrowsException() {
        Config config = Config.builder().name("unknown").value("val").build();
        when(repository.findByName("unknown")).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            configService.update(config);
        });
        assertTrue(ex.getMessage().contains("does not exist"));
    }

    @Test
    void testSoftDelete_Success() {
        String name = "temp";
        Config config = Config.builder().name(name).value("1").build();

        when(repository.findByName(name)).thenReturn(Optional.of(config));
        doNothing().when(repository).softDelete(name);

        configService.softDelete(name);

        verify(repository).softDelete(name);
    }

    @Test
    void testSoftDelete_NameMissing_ThrowsException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            configService.softDelete("");
        });
        assertEquals("Configuration name is required", ex.getMessage());
    }

    @Test
    void testSoftDelete_NotFound_ThrowsException() {
        String name = "nonexistent";
        when(repository.findByName(name)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            configService.softDelete(name);
        });
        assertTrue(ex.getMessage().contains("does not exist"));
    }
}