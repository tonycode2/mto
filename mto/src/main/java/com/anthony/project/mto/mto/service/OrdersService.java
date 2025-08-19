package com.anthony.project.mto.mto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.anthony.project.mto.mto.dto.OrdersDto;
import com.anthony.project.mto.mto.enums.SuitStatusEnum;
import com.anthony.project.mto.mto.model.Clients;
import com.anthony.project.mto.mto.model.Orders;
import com.anthony.project.mto.mto.model.Suits;
import com.anthony.project.mto.mto.repository.ClientsRepo;
import com.anthony.project.mto.mto.repository.OrdersRepo;
import com.anthony.project.mto.mto.repository.SuitsRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OrdersService {
    private final OrdersRepo repo;
    private final ClientsRepo clientsRepo;
    private final SuitsRepo suitsRepo;

    public OrdersService(OrdersRepo repo, ClientsRepo clientsRepo, SuitsRepo suitsRepo) {
        this.repo = repo;
        this.clientsRepo = clientsRepo;
        this.suitsRepo = suitsRepo;
    }

    public OrdersDto getById(Long id) {
        Optional<Orders> order = repo.findById(id);
        if (!order.isPresent()) {
            log.warn("La orden no ha sido encontrada");
            throw new IllegalArgumentException("La orden no ha sido encontrada");
        }
        log.info("La orden ha sido encontrada con exito y la informacion ha sido enviada");
        return (ordersToDto(order.get()));
    }

    public List<OrdersDto> getAll() {
        List<Orders> orders = repo.findAll();
        if (orders.isEmpty()) {
            log.warn("La lista de ordenes esta vacia");
        }
        return listToDto(orders);
    }

    public Orders save(OrdersDto dto) {
        if (dto == null) {
            log.warn("La orden ingresada no es valida");
            throw new IllegalArgumentException("Es posible que la orden ingresada sea null");
        }
        Orders order = dtoToOrders(dto);
        repo.save(order);
        log.info("La orden fue guardada exitosamente");
        return order;
    }

    // Las ordenes no pueden ser editadas. Solo se podria borrar
    // Aunque de ser necesario se podria programar la funcion por aca

    public Boolean delete(Long id) {
        Optional<Orders> order = repo.findById(id);
        if (!order.isPresent()) {
            log.warn("La orden no pudo ser encontrada");
            return false;
        }
        return true;

    }

    private OrdersDto ordersToDto(Orders orders) {
        OrdersDto dto = OrdersDto.builder()
                .clientId(orders.getClient().getId())
                .suitId(orders.getSuits().getId())
                .status(orders.getStatus().name())
                .build();
        return dto;
    }

    private Orders dtoToOrders(OrdersDto dto) {
        Optional<Clients> client = clientsRepo.findById(dto.getClientId());
        Optional<Suits> suit = suitsRepo.findById(dto.getSuitId());
        if (!client.isPresent()) {
            log.warn("El cliente no ha sido encontrado al intentar mapear el dto");
            throw new IllegalArgumentException("El cliente no ha sido encontrado al intentar mapear el dto");
        } else if (!suit.isPresent()) {
            log.warn("El traje no ha sido encontrado al intentar mapear el dto");
            throw new IllegalArgumentException("El traje no ha sido encontrado al intentar mapear el dto");
        }

        Orders order = Orders.builder()
                .client(client.get())
                .suits(suit.get())
                .status(SuitStatusEnum.valueOf(dto.getStatus()))
                .build();
        return order;
    }

    private List<OrdersDto> listToDto(List<Orders> orders) {
        return orders.stream().map(order -> ordersToDto(order)).toList();
    }
}
