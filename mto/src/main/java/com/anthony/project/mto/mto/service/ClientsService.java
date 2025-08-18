package com.anthony.project.mto.mto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anthony.project.mto.mto.dto.ClientsDto;
import com.anthony.project.mto.mto.model.Clients;
import com.anthony.project.mto.mto.repository.ClientsRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ClientsService {
    private final ClientsRepo repo;

    public ClientsService(ClientsRepo repo) {
        this.repo = repo;
    }

    public ClientsDto getById(Long id) {
        Optional<Clients> client = repo.findById(id);
        if (!client.isPresent()) {
            log.warn("El cliente no ha sido encontrado");
            throw new IllegalArgumentException("El cliente no ha sido encontrado");
        }
        log.info("Cliente encontrado exitosamente y la informacion ha sido enviada");
        return clientsToDto(client.get());
    }

    public List<ClientsDto> getAll() {
        List<Clients> clients = repo.findAll();
        return listToDtoList(clients);
    }

    public ClientsDto save(Clients clients) {
        Clients savedClient = repo.save(clients);
        if (savedClient == null) {
            log.warn("Hubo un error al momento de guardar el cliente");
            throw new IllegalArgumentException("El cliente no pudo ser guardado");
        }
        log.info("El cliente fue guardado exitosamente");
        return clientsToDto(clients);
    }

    public ClientsDto update(Long id, ClientsDto updatedClient) {
        Optional<Clients> clientOpt = repo.findById(id);
        if (!clientOpt.isPresent()) {
            log.warn("El cliente que se desea actualizar no fue encontrado");
            throw new IllegalArgumentException("El cliente no fue encontrado");
        }
        Clients client = clientOpt.get();
        client.setName(updatedClient.getName());
        client.setAddress(updatedClient.getAddress());
        client.setPhone(updatedClient.getPhone());
        client.setEmail(updatedClient.getEmail());
        client.setCard(updatedClient.getCard());
        Clients updatedValues = repo.save(client);
        log.info("La informacion del cliente se ha actualizado de manera correcta");
        return clientsToDto(updatedValues);
    }

    public Boolean delete(Long id) {
        Optional<Clients> client = repo.findById(id);
        if (!client.isPresent()) {
            log.warn("El cliente no ha sido encontrado");
            return false;
        }
        repo.deleteById(id);
        log.info("Cliente encontrado exitosamente y eliminado");
        return true;
    }

    private ClientsDto clientsToDto(Clients clients) {
        ClientsDto dto = ClientsDto.builder()
                .name(clients.getName())
                .address(clients.getAddress())
                .card(clients.getCard())
                .email(clients.getEmail())
                .phone(clients.getPhone())
                .build();
        return dto;
    }

    private List<ClientsDto> listToDtoList(List<Clients> clients) {
        return clients.stream().map(client -> clientsToDto(client)).toList();
    }
}
