package com.anthony.project.mto.mto.model.suit;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
@Table(name = "pants")
public class Pants {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double length;
    Double inside; // tiro
    Double waist;
    Double hip;
    Double knee;
    Double below; // ruedo
    String details;
    String fabric;
}
