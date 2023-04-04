package com.myproject.ship.controller;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.ship.dao.ShipRepository;
import com.myproject.ship.entity.Ship;
import com.myproject.ship.errors.ErrorMessage;
import com.myproject.ship.service.ShipService;

@RestController
@RequestMapping("/api/ships")
public class ShipController {

	@Autowired
	ShipRepository shipRepo;

	@Autowired
	ShipService shipService;

	@GetMapping
	public ResponseEntity getAllShips() {
		ArrayList<Ship> ships = (ArrayList<Ship>) shipService.getAllShips();
		if (ships.size() == 0) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ships);
		} else {
			return (ResponseEntity) ResponseEntity.status(HttpStatus.OK).body(ships);
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getId(@PathVariable(value = "id") Long shipId) {
		Optional<Ship> ship = shipRepo.findById(shipId);
		if (ship.isPresent())
			return ResponseEntity.status(HttpStatus.OK).body((ship));
		else
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ship);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deleteById(@PathVariable("id") Long id) {
		try {
			shipService.deleteShipById(id);
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@PostMapping("/ship")
	public ResponseEntity addShip(@RequestBody Ship ship) {
		try {
			Ship savedShip = shipRepo.save(ship);
			return ResponseEntity.status(HttpStatus.CREATED).body(savedShip);
		} catch (Exception e) {
			ErrorMessage errorMessage = new ErrorMessage(e.getMessage());
			return ResponseEntity.badRequest().body(errorMessage);
		}
	}

	@PutMapping("/ship")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateShip(@RequestBody Ship ship) {
		shipRepo.save(ship);
	}

}
