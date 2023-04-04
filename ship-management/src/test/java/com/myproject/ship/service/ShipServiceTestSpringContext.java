package com.myproject.ship.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.myproject.ship.dao.ShipRepository;
import com.myproject.ship.entity.Ship;

@SpringBootTest
class ShipServiceTestSpringContext {
	Ship ship;

	@Autowired
	ShipService shipService;

	@MockBean
	ShipRepository shipRepo;

	@BeforeEach
	void setUp() {
		ship = buildShip();
	}

	@Test
	void testGetAllShips() throws Exception {
		List<Ship> ships = new ArrayList<Ship>();
		ships.add(ship);
		when(shipRepo.findAll()).thenReturn(ships);

		List<Ship> foundShips = shipService.getAllShips();

		assertThat(foundShips.size()).isEqualTo(1);
		assertEquals(ship.getClassId(), foundShips.get(0).getClassId());
		assertEquals(ship.getCargoRegNum(), foundShips.get(0).getCargoRegNum());
		assertEquals(ship.getShipName(), foundShips.get(0).getShipName());
		assertEquals(ship.getCountryOfOrigin(), foundShips.get(0).getCountryOfOrigin());
		assertEquals(ship.getDeparturePort(), foundShips.get(0).getDeparturePort());
		assertEquals(ship.getDestination(), foundShips.get(0).getDestination());
	}

	@Test
	void testGetShipById() throws Exception {
		Ship ship = buildShip();
		Optional<Ship> shipOpt = Optional.of(ship);
		when(shipRepo.findById(any())).thenReturn(shipOpt);

		Optional<Ship> foundShips = shipService.getShipById(1L);

		assertEquals(ship.getClassId(), foundShips.get().getClassId());
		assertEquals(ship.getCargoRegNum(), foundShips.get().getCargoRegNum());
		assertEquals(ship.getShipName(), foundShips.get().getShipName());
		assertEquals(ship.getCountryOfOrigin(), foundShips.get().getCountryOfOrigin());
		assertEquals(ship.getDeparturePort(), foundShips.get().getDeparturePort());
		assertEquals(ship.getDestination(), foundShips.get().getDestination());
	}

	@Test
	public void testDeleteShipById() throws Exception {
		Ship ship = buildShip();
		doNothing().when(shipRepo).deleteById(1L);
		shipService.deleteShipById(1L);

	}

	Ship buildShip() {
		Ship ship = new Ship();
		ship.setId(1L);
		ship.setClassId(1L);
		ship.setCargoRegNum(1L);
		ship.setShipName("Aruba");
		ship.setCountryOfOrigin("China");
		ship.setDeparturePort("Huang");
		ship.setDestination("New York");
		return ship;

	}
}
