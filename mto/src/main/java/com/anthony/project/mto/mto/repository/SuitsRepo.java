package com.anthony.project.mto.mto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anthony.project.mto.mto.model.Suits;

@Repository
public interface SuitsRepo extends JpaRepository<Suits, Long> {

}
