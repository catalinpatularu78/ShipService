package com.myproject.ship.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myproject.ship.entity.Ship;

@Repository
public interface ShipRepository extends JpaRepository<Ship, Long> {

}
