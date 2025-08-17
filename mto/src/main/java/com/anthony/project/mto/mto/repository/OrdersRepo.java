package com.anthony.project.mto.mto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anthony.project.mto.mto.model.Orders;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {

}
