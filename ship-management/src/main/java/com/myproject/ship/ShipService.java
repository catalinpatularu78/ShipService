package com.myproject.ship;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.ship.dao.ShipRepository;
import com.myproject.ship.entity.Ship;

@RestController
@Service
public class ShipService {

	@Autowired
	ShipRepository prodRepo;

	@GetMapping(value = "/ships")
	List<Ship> getshipForCategory() {
		return prodRepo.findAll();
	}

	@GetMapping("/ships/{id}")
	Optional<Ship> getship(@PathVariable("id") Long id) {
		return prodRepo.findById(id);
	}

	@PostMapping(value = "/ships")
	ResponseEntity<Ship> insertship(@RequestBody Ship ship) {
		Ship savedship = prodRepo.save(ship);
		return new ResponseEntity<Ship>(savedship, HttpStatus.OK);
	}

//	@PostMapping(value="/ships")
//	ResponseEntity<Ship> insertship(@Valid @RequestBody Ship ship) {
//		Ship savedship = prodRepo.save(ship) ;
//		URI location= ServletUriComponentsBuilder
//				.fromCurrentRequest().path("/{id}")
//				.buildAndExpand(savedship.getId())
//				.toUri();
//		return ResponseEntity.created(location).build() ;		
//	}

	@DeleteMapping(value = "/ships/{id}")
	ResponseEntity<Ship> deleteship(@PathVariable("id") Long id) {

		// First fetch an existing ship and then delete it.
		Optional<Ship> optionalship = prodRepo.findById(id);
		Ship existingship = optionalship.get();
		// Return the deleted ship
		prodRepo.delete(existingship);
		return new ResponseEntity<Ship>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(value = "/ships/{id}")
	ResponseEntity updateship(@PathVariable("id") Long id, @RequestBody Ship ship) {

		// First fetch an existing ship and then modify it.
		Optional<Ship> optionalship = prodRepo.findById(id);
		Ship existingship = optionalship.get();
		// Now update it back
		existingship.setClassId(ship.getClassId());
		existingship.setShipName(ship.getShipName());
		Ship savedship = prodRepo.save(existingship);
		// Return the updated ship
		return new ResponseEntity<Ship>(savedship, HttpStatus.OK);
	}

}
