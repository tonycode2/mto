package com.anthony.project.mto.mto.repository.suit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anthony.project.mto.mto.model.suit.Shoes;

@Repository
public interface ShoesRepo extends JpaRepository<Shoes, Long> {

}
