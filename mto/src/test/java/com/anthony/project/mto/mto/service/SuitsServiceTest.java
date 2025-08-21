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

import com.anthony.project.mto.mto.model.Suits;
import com.anthony.project.mto.mto.model.suit.Accessories;
import com.anthony.project.mto.mto.model.suit.Jackets;
import com.anthony.project.mto.mto.model.suit.Pants;
import com.anthony.project.mto.mto.model.suit.Shirts;
import com.anthony.project.mto.mto.model.suit.Shoes;
import com.anthony.project.mto.mto.model.suit.Vests;
import com.anthony.project.mto.mto.repository.SuitsRepo;
import com.anthony.project.mto.mto.repository.suit.AccessoriesRepo;
import com.anthony.project.mto.mto.repository.suit.JacketsRepo;
import com.anthony.project.mto.mto.repository.suit.PantsRepo;
import com.anthony.project.mto.mto.repository.suit.ShirtsRepo;
import com.anthony.project.mto.mto.repository.suit.ShoesRepo;
import com.anthony.project.mto.mto.repository.suit.VestsRepo;

@ExtendWith(MockitoExtension.class)
public class SuitsServiceTest {
	@Mock
	private SuitsRepo suitsRepo;
	@Mock
	private AccessoriesRepo accessoriesRepo;
	@Mock
	private JacketsRepo jacketsRepo;
	@Mock
	private PantsRepo pantsRepo;
	@Mock
	private ShirtsRepo shirtsRepo;
	@Mock
	private ShoesRepo shoesRepo;
	@Mock
	private VestsRepo vestsRepo;

	@InjectMocks
	private SuitsService service;

	@Captor
	private ArgumentCaptor<Suits> suitsCaptor;

	private Accessories sampleAccessories;
	private Jackets samplJackets;
	private Pants samplePants;
	private Shirts sampleShirts;
	private Shoes sampleShoes;
	private Vests sampleVests;
	private Suits sampleSuit;

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
	}

	@Test
	void getById_whenFound_returnsSuit() {
		when(suitsRepo.findById(1L)).thenReturn(Optional.of(sampleSuit));

		Suits response = service.getById(1L);

		assertNotNull(response);
		assertEquals("Mancuernas", response.getAccessories().getDetails());
		assertEquals("Corto", response.getVests().getDetails());
		assertEquals("Blanca", response.getShirts().getDetails());
		verify(suitsRepo).findById(1L);
		verifyNoMoreInteractions(suitsRepo);
	}

	@Test
	void getById_whenNotFound_throwsIllegalArgumentException() {
		when(suitsRepo.findById(99L)).thenReturn(Optional.empty());

		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.getById(99L));
		assertTrue(ex.getMessage().toLowerCase().contains("no pudo ser encontrado"));
		verify(suitsRepo).findById(99L);
		verifyNoMoreInteractions(suitsRepo);

	}

	@Test
	void getAll_whenHasData_returnsList() {
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
		when(suitsRepo.findAll()).thenReturn(List.of(s1, s2));

		List<Suits> response = service.getAll();
		assertEquals(1L, response.get(0).getId());
		assertEquals(2L, response.get(1).getId());
		verify(suitsRepo).findAll();
		verifyNoMoreInteractions(suitsRepo);
	}

	@Test
	void getAll_whenEmpty_returnsEmptyList() {
		when(suitsRepo.findAll()).thenReturn(Collections.emptyList());

		List<Suits> response = service.getAll();

		assertNotNull(response);
		assertTrue(response.isEmpty());
		verify(suitsRepo).findAll();
		verifyNoMoreInteractions(suitsRepo);
	}

	@Test
	void save_whenNull_throwsIllegalArgumentException() {
		IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> service.save(null));
		assertTrue(ex.getMessage().toLowerCase().contains("null"));
		verify(suitsRepo, never()).save(any());
		verifyNoMoreInteractions(suitsRepo);
	}

	@Test
	void save_whenValid_callsRepoSave_andReturnsSuit() {
		when(accessoriesRepo.save(any(Accessories.class))).thenAnswer(inv -> inv.getArgument(0));
		when(jacketsRepo.save(any(Jackets.class))).thenAnswer(inv -> inv.getArgument(0));
		when(shirtsRepo.save(any(Shirts.class))).thenAnswer(inv -> inv.getArgument(0));
		when(shoesRepo.save(any(Shoes.class))).thenAnswer(inv -> inv.getArgument(0));
		when(pantsRepo.save(any(Pants.class))).thenAnswer(inv -> inv.getArgument(0));
		when(vestsRepo.save(any(Vests.class))).thenAnswer(inv -> inv.getArgument(0));
		when(suitsRepo.save(any(Suits.class))).thenAnswer(inv -> inv.getArgument(0));

		verify(suitsRepo).save(suitsCaptor.capture());
		Suits saved = suitsCaptor.getValue();
		assertEquals("Algodon", saved.getShirts().getFabric());
		verify(suitsRepo).save(any(Suits.class));
		verifyNoMoreInteractions(suitsRepo);
	}

	@Test
	void delete_whenFound_callsDeleteById_andReturnsTrue() {
		when(suitsRepo.findById(1L)).thenReturn(Optional.of(sampleSuit));

		boolean response = service.delete(1L);

		assertTrue(response);
		verify(suitsRepo).findById(1L);
		verify(suitsRepo).deleteById(1L);
		verifyNoMoreInteractions(suitsRepo);
	}

	@Test
	void delete_whenNotFound_returnsFalse_andDoesNotCallDeleteById() {
		when(suitsRepo.findById(99L)).thenReturn(Optional.empty());

		boolean response = service.delete(99L);

		assertFalse(response);
		verify(suitsRepo).findById(99L);
		verify(suitsRepo, never()).deleteById(99L);
		verifyNoMoreInteractions(suitsRepo);
	}
}
