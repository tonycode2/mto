package com.anthony.project.mto.mto.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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

import com.anthony.project.mto.mto.dto.OrdersDto;
import com.anthony.project.mto.mto.enums.SuitStatusEnum;
import com.anthony.project.mto.mto.model.Clients;
import com.anthony.project.mto.mto.model.Orders;
import com.anthony.project.mto.mto.model.Suits;
import com.anthony.project.mto.mto.model.suit.Accessories;
import com.anthony.project.mto.mto.model.suit.Jackets;
import com.anthony.project.mto.mto.model.suit.Pants;
import com.anthony.project.mto.mto.model.suit.Shirts;
import com.anthony.project.mto.mto.model.suit.Shoes;
import com.anthony.project.mto.mto.model.suit.Vests;
import com.anthony.project.mto.mto.repository.ClientsRepo;
import com.anthony.project.mto.mto.repository.OrdersRepo;
import com.anthony.project.mto.mto.repository.SuitsRepo;

@ExtendWith(MockitoExtension.class)
public class OrdersServiceTest {
        @Mock
        private OrdersRepo ordersRepo;
        @Mock
        private ClientsRepo clientsRepo;
        @Mock
        private SuitsRepo suitsRepo;

        @InjectMocks
        private OrdersService service;

        @Captor
        private ArgumentCaptor<Orders> orderdCaptor;

        private Orders sampleOrder;
        private Clients sampleClient;
        private Suits sampleSuit;
        private Accessories sampleAccessories;
        private Jackets samplJackets;
        private Pants samplePants;
        private Shirts sampleShirts;
        private Shoes sampleShoes;
        private Vests sampleVests;

        @BeforeEach
        void setUp() {
                sampleAccessories = Accessories.builder()
                                .id(1L)
                                .details("Mancuernas")
                                .build();
                samplJackets = Jackets.builder()
                                .id(1L)
                                .back(1.0)
                                .chest(1.0)
                                .elbow(1.0)
                                .fist(1.0)
                                .fit(1.0)
                                .hip(1.0)
                                .fabric("Lana")
                                .details("Corto")
                                .build();
                samplePants = Pants.builder()
                                .id(1L)
                                .below(1.0)
                                .hip(1.0)
                                .inside(1.0)
                                .knee(1.0)
                                .length(1.0)
                                .waist(1.0)
                                .details("Corto")
                                .fabric("Lana")
                                .build();
                sampleShirts = Shirts.builder()
                                .id(1L)
                                .neck(1.0)
                                .sleeve(1.0)
                                .fabric("Algodon")
                                .details("Blanca")
                                .build();
                sampleShoes = Shoes.builder()
                                .id(1L)
                                .leather("Gris")
                                .size(32.0)
                                .build();
                sampleVests = Vests.builder()
                                .id(1L)
                                .back(1.0)
                                .bm(1.0)
                                .chest(1.0)
                                .hip(1.0)
                                .length(1.0)
                                .shoulder(1.0)
                                .waist(1.0)
                                .vline(1.0)
                                .details("Corto")
                                .fabric("Lana")
                                .build();
                sampleSuit = Suits.builder()
                                .id(1L)
                                .accessories(sampleAccessories)
                                .jackets(samplJackets)
                                .pants(samplePants)
                                .shirts(sampleShirts)
                                .shoes(sampleShoes)
                                .vests(sampleVests)
                                .build();
                sampleClient = Clients.builder()
                                .id(1L)
                                .name("Ana")
                                .address("CR-123")
                                .phone("2222-3333")
                                .email("ana@example.com")
                                .card("4111111111111111")
                                .build();
                sampleOrder = Orders.builder()
                                .id(1L)
                                .client(sampleClient)
                                .suits(sampleSuit)
                                .status(SuitStatusEnum.CREATED)
                                .build();

        }

        @Test
        void getById_whenFound_returnsDto() {
                when(ordersRepo.findById(1L)).thenReturn(Optional.of(sampleOrder));

                OrdersDto dto = service.getById(1L);

                assertNotNull(dto);
                assertEquals(sampleOrder.getClient().getId(), dto.getClientId());
                assertEquals(sampleOrder.getSuits().getId(), dto.getSuitId());
                assertEquals(sampleOrder.getStatus().name(), dto.getStatus());
                verify(ordersRepo).findById(1L);
                verifyNoMoreInteractions(ordersRepo);
        }

        @Test
        void getById_whenNotFound_throwsIllegalArgumentException() {
                when(ordersRepo.findById(99L)).thenReturn(Optional.empty());

                IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.getById(99L));

                assertTrue(ex.getMessage().toLowerCase().contains("no ha sido encontrada"));
                verify(ordersRepo).findById(99L);
                verifyNoMoreInteractions(ordersRepo);
        }

        @Test
        void getAll_whenEmpty_returnsEmptyList() {
                when(ordersRepo.findAll()).thenReturn(Collections.emptyList());

                List<OrdersDto> result = service.getAll();

                assertNotNull(result);
                assertTrue(result.isEmpty());
                verify(ordersRepo).findAll();
                verifyNoMoreInteractions(ordersRepo);
        }

        @Test
        void getAll_whenHasData_mapsToDtoList() {
                Accessories a1 = Accessories.builder()
                                .id(1L)
                                .details("Mancuernas")
                                .build();
                Jackets j1 = Jackets.builder()
                                .id(1L)
                                .back(1.0)
                                .chest(1.0)
                                .elbow(1.0)
                                .fist(1.0)
                                .fit(1.0)
                                .hip(1.0)
                                .fabric("Lana")
                                .details("Corto")
                                .build();
                Pants p1 = Pants.builder()
                                .id(1L)
                                .below(1.0)
                                .hip(1.0)
                                .inside(1.0)
                                .knee(1.0)
                                .length(1.0)
                                .waist(1.0)
                                .details("Corto")
                                .fabric("Lana")
                                .build();
                Shirts shi1 = Shirts.builder()
                                .id(1L)
                                .neck(1.0)
                                .sleeve(1.0)
                                .fabric("Algodon")
                                .details("Blanca")
                                .build();
                Shoes sho1 = Shoes.builder()
                                .id(1L)
                                .leather("Gris")
                                .size(32.0)
                                .build();
                Vests v1 = Vests.builder()
                                .id(1L)
                                .back(1.0)
                                .bm(1.0)
                                .chest(1.0)
                                .hip(1.0)
                                .length(1.0)
                                .shoulder(1.0)
                                .waist(1.0)
                                .vline(1.0)
                                .details("Corto")
                                .fabric("Lana")
                                .build();
                Suits s1 = Suits.builder()
                                .id(1L)
                                .accessories(a1)
                                .jackets(j1)
                                .pants(p1)
                                .shirts(shi1)
                                .shoes(sho1)
                                .vests(v1)
                                .build();
                Clients c1 = Clients.builder()
                                .id(1L)
                                .name("Ana")
                                .address("CR-123")
                                .phone("2222-3333")
                                .email("ana@example.com")
                                .card("4111111111111111")
                                .build();
                Orders o1 = Orders.builder()
                                .id(1L)
                                .client(c1)
                                .suits(s1)
                                .status(SuitStatusEnum.CREATED)
                                .build();
                Accessories a2 = Accessories.builder()
                                .id(2L)
                                .details("Mancuernas")
                                .build();
                Jackets j2 = Jackets.builder()
                                .id(2L)
                                .back(2.0)
                                .chest(2.0)
                                .elbow(2.0)
                                .fist(2.0)
                                .fit(2.0)
                                .hip(2.0)
                                .fabric("Lana")
                                .details("Corto")
                                .build();
                Pants p2 = Pants.builder()
                                .id(2L)
                                .below(2.0)
                                .hip(2.0)
                                .inside(2.0)
                                .knee(2.0)
                                .length(2.0)
                                .waist(2.0)
                                .details("Corto")
                                .fabric("Lana")
                                .build();
                Shirts shi2 = Shirts.builder()
                                .id(2L)
                                .neck(2.0)
                                .sleeve(2.0)
                                .fabric("Algodon")
                                .details("Blanca")
                                .build();
                Shoes sho2 = Shoes.builder()
                                .id(2L)
                                .leather("Gris")
                                .size(36.0)
                                .build();
                Vests v2 = Vests.builder()
                                .id(2L)
                                .back(2.0)
                                .bm(2.0)
                                .chest(2.0)
                                .hip(2.0)
                                .length(2.0)
                                .shoulder(2.0)
                                .waist(2.0)
                                .vline(2.0)
                                .details("Corto")
                                .fabric("Lana")
                                .build();
                Suits s2 = Suits.builder()
                                .id(2L)
                                .accessories(a2)
                                .jackets(j2)
                                .pants(p2)
                                .shirts(shi2)
                                .shoes(sho2)
                                .vests(v2)
                                .build();
                Clients c2 = Clients.builder()
                                .id(2L)
                                .name("Marco")
                                .address("CR-456")
                                .phone("8888-9999")
                                .email("marco@example.com")
                                .card("5555555555554444")
                                .build();
                Orders o2 = Orders.builder()
                                .id(2L)
                                .client(c2)
                                .suits(s2)
                                .status(SuitStatusEnum.CUTTED)
                                .build();

                when(ordersRepo.findAll()).thenReturn(List.of(o1, o2));

                List<OrdersDto> result = service.getAll();

                assertEquals(2L, result.get(1).getClientId());
                assertEquals(1L, result.get(0).getClientId());
                assertEquals(SuitStatusEnum.CREATED.toString(), result.get(0).getStatus());
                assertEquals(SuitStatusEnum.CUTTED.toString(), result.get(1).getStatus());
                verify(ordersRepo).findAll();
                verifyNoMoreInteractions(ordersRepo);
        }

        @Test
        void save_whenNull_throwsIllegalArgumentException() {
                IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.save(null));

                assertTrue(ex.getMessage().toLowerCase().contains("null"));
                verify(ordersRepo, never()).save(any());
                verifyNoMoreInteractions(ordersRepo);
        }

        @Test
        void delete_whenNotFound_returnsFalse_andDoesNotCallDeleteById() {
                when(ordersRepo.findById(123L)).thenReturn(Optional.empty());

                boolean result = service.delete(123L);

                assertFalse(result);
                verify(ordersRepo).findById(123L);
                verify(ordersRepo, never()).deleteById(123L);
                verifyNoMoreInteractions(ordersRepo);
        }

        @Test
        void delete_whenFound_callsDeleteById_andReturnsTrue() {
                when(ordersRepo.findById(1L)).thenReturn(Optional.of(sampleOrder));

                boolean result = service.delete(1L);

                assertTrue(result);
                verify(ordersRepo).findById(1L);
                verify(ordersRepo).deleteById(1L);
                verifyNoMoreInteractions(ordersRepo);
        }

}
