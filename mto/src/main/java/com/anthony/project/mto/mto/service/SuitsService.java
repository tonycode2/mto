package com.anthony.project.mto.mto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SuitsService {
    private final SuitsRepo repo;
    private final AccessoriesRepo accessoriesRepo;
    private final JacketsRepo jacketsRepo;
    private final PantsRepo pantsRepo;
    private final ShirtsRepo shirtsRepo;
    private final ShoesRepo shoesRepo;
    private final VestsRepo vestsRepo;

    public SuitsService(SuitsRepo repo, AccessoriesRepo accessoriesRepo, JacketsRepo jacketsRepo, PantsRepo pantsRepo,
            ShirtsRepo shirtsRepo, ShoesRepo shoesRepo, VestsRepo vestsRepo) {
        this.repo = repo;
        this.accessoriesRepo = accessoriesRepo;
        this.jacketsRepo = jacketsRepo;
        this.pantsRepo = pantsRepo;
        this.shirtsRepo = shirtsRepo;
        this.shoesRepo = shoesRepo;
        this.vestsRepo = vestsRepo;
    }

    public Suits getById(Long id) {
        Optional<Suits> suit = repo.findById(id);
        if (!suit.isPresent()) {
            log.warn("El traje no pudo ser encontrado");
            throw new IllegalArgumentException("El traje no pudo ser encontrado");
        }
        log.info("El traje fue encontrado y la informacion ha sido enviada");
        return suit.get();
        // Aqui no regreso un dto porque me sirve mas tener toda la info del traje
    }

    public List<Suits> getAll() {
        List<Suits> suits = repo.findAll();
        return suits;
    }

    @Transactional
    public Suits save(Suits suits) {
        if (suits == null) {
            log.warn("El traje no puede ser guardado");
            throw new IllegalArgumentException("Es posible que el traje sea null");
        }
        Accessories accessories = accessoriesRepo.save(suits.getAccessories());
        Jackets jackets = jacketsRepo.save(suits.getJackets());
        Pants pants = pantsRepo.save(suits.getPants());
        Shirts shirts = shirtsRepo.save(suits.getShirts());
        Shoes shoes = shoesRepo.save(suits.getShoes());
        Vests vests = vestsRepo.save(suits.getVests());

        Suits suit = Suits.builder()
                .accessories(accessories)
                .jackets(jackets)
                .pants(pants)
                .shirts(shirts)
                .shoes(shoes)
                .vests(vests)
                .build();
        Suits savedSuit = repo.save(suit);
        log.info("El traje ha sido guardado exitosamente");
        return savedSuit;
    }

    public Suits update(Suits updatedSuit) {
        Optional<Suits> suitsOpt = repo.findById(updatedSuit.getId());
        if (!suitsOpt.isPresent()) {
            log.warn("El traje no pudo ser encontrado");
            throw new IllegalArgumentException("El traje no pudo ser encontrado");
        }
        Suits suit = suitsOpt.get();
        suit.setAccessories(updatedSuit.getAccessories());
        suit.setJackets(updatedSuit.getJackets());
        suit.setPants(updatedSuit.getPants());
        suit.setShirts(updatedSuit.getShirts());
        suit.setShoes(updatedSuit.getShoes());
        suit.setVests(updatedSuit.getVests());
        Suits updatedValues = repo.save(suit);
        log.info("El traje ha sido actualizado");
        return updatedValues;
    }

    public Boolean delete(Long id) {
        Optional<Suits> suit = repo.findById(id);
        if (!suit.isPresent()) {
            log.warn("El traje no pudo ser encontrado");
            return false;
        }
        repo.deleteById(id);
        log.info("El traje fue eliminado");
        return true;
    }

}