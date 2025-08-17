package com.anthony.project.mto.mto.model;

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
@Table(name = "suits")
public class Suits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}
