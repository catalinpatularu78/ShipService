package com.myproject.ship.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myproject.ship.dao.ShipRepository;
import com.myproject.ship.entity.Ship;

@Service
public class ShipService {
	@Autowired
	ShipRepository repository;

	public List<Ship> getAllShips() {
		return repository.findAll();
	}

	public Optional<Ship> getShipById(Long id) {
		return repository.findById(id);
	}

	public void deleteShipById(Long id) {
		repository.deleteById(id);
	}

}
