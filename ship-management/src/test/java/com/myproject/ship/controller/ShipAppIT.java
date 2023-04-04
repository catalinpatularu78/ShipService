package com.myproject.ship.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

import com.myproject.ship.dao.ShipRepository;
import com.myproject.ship.entity.Ship;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ShipAppIT {

	@Value(value = "${local.server.port}")
	private int port;

	TestRestTemplate restTemplate;
	HttpHeaders headers;

	@Autowired
	ShipController shipController;

	@Autowired
	ShipRepository shipRepo;

	@BeforeEach
	public void setup() {
		restTemplate = new TestRestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	@Sql({ "/testdata.sql" })
	public void addShipTest() {
		HttpEntity<Ship> request = new HttpEntity<Ship>(
				buildShip(264535335, "ESPINOZA", 200, "SPAIN", "BARCELONA", "MALLORCA"), headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/ships/ship",
				request, String.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	@Sql({ "/testdata.sql" })
	public void addShipBadRequestTest() {
		HttpEntity<Ship> request = new HttpEntity<Ship>(null, headers);
		ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/api/ships/ship",
				request, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void getShipByIdTest() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/ships/1",
				String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void getShipByIdNotFoundTest() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/ships/9",
				String.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void deleteShipByIdTest() {
		ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/api/ships/1",
				HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void deleteShipByIdNotFoundTest() {
		ResponseEntity<Void> response = restTemplate.exchange("http://localhost:" + port + "/api/ships/9",
				HttpMethod.DELETE, HttpEntity.EMPTY, Void.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@Sql({ "/testdata.sql" })
	public void getAllShipsEmptyListTest() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/ships/0",
				String.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

	}

	@Test
	public void getAllShipsTest() {
		ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port + "/api/ships",
				String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	Ship buildShip(long cargoRegNum, String shipName, long classId, String countryOfOrigin, String departurePort,
			String destination) {
		Ship ship = new Ship();
		ship.setCargoRegNum(cargoRegNum);
		ship.setShipName(shipName);
		ship.setClassId(classId);
		ship.setCountryOfOrigin(countryOfOrigin);
		ship.setDeparturePort(departurePort);
		ship.setDestination(destination);

		return ship;
	}
}
