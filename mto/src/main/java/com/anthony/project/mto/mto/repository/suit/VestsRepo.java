package com.anthony.project.mto.mto.repository.suit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anthony.project.mto.mto.model.suit.Vests;

@Repository
public interface VestsRepo extends JpaRepository<Vests, Long> {

}
