package com.anthony.project.mto.mto.model;

import com.anthony.project.mto.mto.model.suit.Accessories;
import com.anthony.project.mto.mto.model.suit.Jackets;
import com.anthony.project.mto.mto.model.suit.Pants;
import com.anthony.project.mto.mto.model.suit.Shirts;
import com.anthony.project.mto.mto.model.suit.Shoes;
import com.anthony.project.mto.mto.model.suit.Vests;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
    @OneToOne
    @JoinColumn(name = "accessory_id")
    Accessories accessories;
    @OneToOne
    @JoinColumn(name = "jacket_id")
    Jackets jackets;
    @OneToOne
    @JoinColumn(name = "pant_id")
    Pants pants;
    @OneToOne
    @JoinColumn(name = "shirt_id")
    Shirts shirts;
    @OneToOne
    @JoinColumn(name = "shoe_id")
    Shoes shoes;
    @OneToOne
    @JoinColumn(name = "vest_id")
    Vests vests;
}
