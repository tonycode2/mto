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
@Table(name = "shirts")
public class Shirts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Double neck; // preguntar que tipo de dato es este
    Double sleeve;
    String details;
    String fabric;
}
