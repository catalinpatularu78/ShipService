package com.myproject.ship.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.ship.dao.ShipRepository;
import com.myproject.ship.entity.Ship;
import com.myproject.ship.service.ShipService;

@SpringBootTest
@AutoConfigureMockMvc
class ShipControllerTest {

	@Autowired
	ShipController shipController;

	@MockBean
	ShipRepository shipRepo;

	@MockBean
	ShipService shipservice;

	@Captor
	ArgumentCaptor<Ship> captor;

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void getAllShipsControllerTest() throws Exception {
		List<Ship> ships = new ArrayList<Ship>();
		ships.add(buildShip());
		when(shipservice.getAllShips()).thenReturn(ships);
		when(shipRepo.findAll()).thenReturn(ships);

		this.mockMvc.perform(get("/api/ships")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Aruba")));
	}

	@Test
	public void getAllShipsEmptyListControllerTest() throws Exception {
		List<Ship> ships = new ArrayList<Ship>();
		when(shipservice.getAllShips()).thenReturn(ships);
		when(shipRepo.findAll()).thenReturn(ships);

		this.mockMvc.perform(get("/api/ships")).andDo(print()).andExpect(status().isNoContent());

	}

	@Test
	public void getShipByIdFoundControllerTest() throws Exception {
		Ship ship = buildShip();
		Optional<Ship> shipOpt = Optional.of(ship);
		when(shipservice.getShipById(any())).thenReturn(shipOpt);
		when(shipRepo.findById(any())).thenReturn(shipOpt);
		this.mockMvc.perform(get("/api/ships/" + ship.getId())).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Aruba")));

	}

	@Test
	public void getShipByIdNoContentControllerTest() throws Exception {
		Ship ship = buildShip();
		this.mockMvc.perform(get("/api/ships/" + ship.getId())).andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	public void deleteShipByIdControllerTest() throws Exception {
		Ship ship = buildShip();
		Optional<Ship> shipOpt = Optional.of(ship);
		when(shipRepo.findById(ship.getId())).thenReturn(shipOpt);
		doNothing().when(shipRepo).delete(ship);
		this.mockMvc.perform(delete("/api/ships/" + ship.getId())).andDo(print()).andExpect(status().isOk());
	}

	@Test
	public void deleteShipByIdNotFoundControllerTest() throws Exception {
		Ship ship = buildShip();
		doThrow(new RuntimeException("Exception")).when(shipservice).deleteShipById(any());
		doThrow(new RuntimeException("Exception")).when(shipRepo).delete(any());
		this.mockMvc.perform(delete("/api/ships/" + ship.getId())).andDo(print()).andExpect(status().isNotFound());
	}

	@Test
	public void addShipControllerTest() throws Exception {
		Ship ship = buildShip();
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(ship);
		when(shipRepo.save(ship)).thenReturn(ship);
		this.mockMvc.perform(post("/api/ships/ship").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andDo(print()).andExpect(status().isCreated());

	}

	@Test
	public void addShipControllerThrowsExceptionTest() throws Exception {
		Ship ship = buildShip();
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(ship);
		doThrow(new RuntimeException("Exception")).when(shipRepo).save(any());

		this.mockMvc.perform(post("/api/ships/ship").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andDo(print()).andExpect(status().isBadRequest());

	}

	@Test
	public void updateShipControllerTest() throws Exception {
		Ship ship = buildShip();
		ObjectMapper map = new ObjectMapper();
		String jsonString = map.writeValueAsString(ship);
		when(shipRepo.save(ship)).thenReturn(ship);
		this.mockMvc.perform(put("/api/ships/ship").contentType(MediaType.APPLICATION_JSON).content(jsonString))
				.andDo(print()).andExpect(status().isCreated());

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
