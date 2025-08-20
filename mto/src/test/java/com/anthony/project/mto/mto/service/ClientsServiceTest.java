package com.anthony.project.mto.mto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.anthony.project.mto.mto.dto.ClientsDto;
import com.anthony.project.mto.mto.model.Clients;
import com.anthony.project.mto.mto.repository.ClientsRepo;

@ExtendWith(MockitoExtension.class)
public class ClientsServiceTest {

    @Mock
    private ClientsRepo repo;

    @InjectMocks
    private ClientsService service;

    @Captor
    private ArgumentCaptor<Clients> clientCaptor;

    private Clients sampleClient;

    @BeforeEach
    void setUp() {
        sampleClient = Clients.builder()
                .id(1L)
                .name("Ana")
                .address("CR-123")
                .phone("2222-3333")
                .email("ana@example.com")
                .card("4111111111111111")
                .build();
    }

    @Test
    void getById_whenFound_returnsDto() {
        when(repo.findById(1L)).thenReturn(Optional.of(sampleClient));
        ClientsDto dto = service.getById(1L);

        assertNotNull(dto);
        assertEquals(sampleClient.getName(), dto.getName());
        assertEquals(sampleClient.getEmail(), dto.getEmail());
        verify(repo).findById(1L);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getById_whenNotFound_throwsIllegalArgumentException() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.getById(99L));
        assertTrue(ex.getMessage().toLowerCase().contains("no ha sido encontrado"));
        verify(repo).findById(99L);
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getAll_whenEmpty_returnsEmptyList() {
        when(repo.findAll()).thenReturn(Collections.emptyList());

        List<ClientsDto> result = service.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repo).findAll();
        verifyNoMoreInteractions(repo);
    }

    @Test
    void getAll_whenHasData_mapsToDtoList() {
        Clients c1 = Clients.builder()
                .id(1L)
                .name("Ana")
                .address("CR-123")
                .phone("2222-3333")
                .email("ana@example.com")
                .card("4111111111111111")
                .build();
        Clients c2 = Clients.builder()
                .id(2L)
                .name("Marco")
                .address("CR-456")
                .phone("8888-9999")
                .email("marco@example.com")
                .card("5555555555554444")
                .build();
        when(repo.findAll()).thenReturn(List.of(c1, c2));

        List<ClientsDto> result = service.getAll();

        assertEquals(2, result.size());
        assertEquals("Ana", result.get(0).getName());
        assertEquals("marco@example.com", result.get(1).getEmail());
        verify(repo).findAll();
        verifyNoMoreInteractions(repo);
    }

    @Test
    void save_whenNull_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.save(null));
        assertTrue(ex.getMessage().toLowerCase().contains("null"));
        verify(repo, never()).save(any());
        verifyNoMoreInteractions(repo);
    }

    @Test
    void save_whenValid_callsRepoSave_andReturnsMappedDto() {
        when(repo.save(any(Clients.class))).thenAnswer(inv -> inv.getArgument(0));

        ClientsDto dto = service.save(sampleClient);

        assertNotNull(dto);
        assertEquals(sampleClient.getName(), dto.getName());
        assertEquals(sampleClient.getEmail(), dto.getEmail());
        verify(repo).save(sampleClient);
        verifyNoMoreInteractions(repo);

    }

    @Test
    void update_whenNotFound_throwsIllegalArgumentException() {
        when(repo.findById(42L)).thenReturn(Optional.empty());

        ClientsDto incomming = ClientsDto.builder()
                .name("Nuevo")
                .address("Nueva dir")
                .phone("0000-0000")
                .email("nuevo@example.com")
                .card("4000000000000002")
                .build();
        assertThrows(IllegalArgumentException.class, () -> service.update(42L, incomming));
        verify(repo).findById(42L);
        verify(repo, never()).save(any());
        verifyNoMoreInteractions(repo);

    }

    @Test
    void update_whenFound_updatesFieldsAndSaves_returnsMappedDto() {
        when(repo.findById(1L)).thenReturn(Optional.of(sampleClient));
        when(repo.save(any(Clients.class))).thenAnswer(inv -> inv.getArgument(0));

        ClientsDto updatedValues = ClientsDto.builder()
                .name("Ana María")
                .address("CR-999")
                .phone("7777-6666")
                .email("ana.maria@example.com")
                .card("4000000000000000")
                .build();
        ClientsDto result = service.update(1L, updatedValues);
        assertEquals("Ana María", result.getName());
        assertEquals("CR-999", result.getAddress());
        assertEquals("ana.maria@example.com", result.getEmail());

        verify(repo).findById(1L);
        verify(repo).save(clientCaptor.capture());

        Clients savedEntity = clientCaptor.getValue();

        assertEquals("Ana María", savedEntity.getName());
        assertEquals("CR-999", savedEntity.getAddress());
        assertEquals("7777-6666", savedEntity.getPhone());
        assertEquals("ana.maria@example.com", savedEntity.getEmail());
        assertEquals("4000000000000000", savedEntity.getCard());
        verifyNoMoreInteractions(repo);
    }

    @Test
    void delete_whenNotFound_returnsFalse_andDoesNotCallDeleteById() {
        when(repo.findById(123L)).thenReturn(Optional.empty());

        boolean result = service.delete(123L);

        assertFalse(result);
        verify(repo).findById(123L);
        verify(repo, never()).deleteById(anyLong());
        verifyNoMoreInteractions(repo);
    }

    @Test
    void delete_whenFound_callsDeleteById_andReturnsTrue() {
        when(repo.findById(1L)).thenReturn(Optional.of(sampleClient));

        boolean result = service.delete(1L);

        assertTrue(result);
        verify(repo).findById(1L);
        verify(repo).deleteById(1L);
        verifyNoMoreInteractions(repo);
    }

}
