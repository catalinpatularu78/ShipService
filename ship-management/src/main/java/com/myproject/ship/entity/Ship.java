package com.myproject.ship.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Ship implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "cargoRegNum")
	private Long cargoRegNum;

	@Size(min = 3)
	@Column(name = "shipName")
	private String shipName;

	@Column(name = "classId", nullable = false)
	private Long classId;

	@Column(name = "countryOfOrigin")
	private String countryOfOrigin;

	@Column(name = "departurePort")
	private String departurePort;

	@Column(name = "destination")
	private String destination;

	public Ship() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCargoRegNum() {
		return cargoRegNum;
	}

	public void setCargoRegNum(Long cargoRegNum) {
		this.cargoRegNum = cargoRegNum;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public long getClassId() {
		return classId;
	}

	public void setClassId(Long classId) {
		this.classId = classId;
	}

	public String getCountryOfOrigin() {
		return countryOfOrigin;
	}

	public void setCountryOfOrigin(String countryOfOrigin) {
		this.countryOfOrigin = countryOfOrigin;
	}

	public String getDeparturePort() {
		return departurePort;
	}

	public void setDeparturePort(String departurePort) {
		this.departurePort = departurePort;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

}
