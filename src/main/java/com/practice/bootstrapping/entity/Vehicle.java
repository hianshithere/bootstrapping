package com.practice.bootstrapping.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "model_vehicle")
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_generator")
	@SequenceGenerator(name="vehicle_generator", sequenceName = "sequence_vehicle")
	private Integer id;

	@Column(name = "vehicle_name", nullable = false, unique = true)
	private String vehicleName;

	@Column(name = "vehicle_description", nullable = true)
	private String vehicleDescription;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVehicleName() {
		return vehicleName;
	}

	public void setVehicleName(String vehicleName) {
		this.vehicleName = vehicleName;
	}

	public String getVehicleDescription() {
		return vehicleDescription;
	}

	public void setVehicleDescription(String vehicleDescription) {
		this.vehicleDescription = vehicleDescription;
	}

	public Vehicle(String vehicleName, String vehicleDescription) {
		super();
		this.vehicleName = vehicleName;
		this.vehicleDescription = vehicleDescription;
	}

	public Vehicle() {
		super();
	}

}
