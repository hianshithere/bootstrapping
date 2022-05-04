package com.practice.bootstrapping.entity;


import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "model_user",
		uniqueConstraints = { 
				@UniqueConstraint(name = "UniqueFirstname",columnNames = { "firstname"}) 
	})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name = "user_generator", sequenceName = "sequence_user")
	private Integer id;

	private String firstname;
	private String lastname;
	private String description;

	@OneToMany
	@JoinTable(name="user_vehicle", joinColumns = @JoinColumn(name = "user_id"),
			   inverseJoinColumns = @JoinColumn(name = "vehicle_id"))
	Collection<Vehicle> collectionOfVehicle = new ArrayList<Vehicle>();;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<Vehicle> getCollectionOfVehicle() {
		return collectionOfVehicle;
	}

	public void setCollectionOfVehicle(Collection<Vehicle> collectionOfVehicle) {
		this.collectionOfVehicle = collectionOfVehicle;
	}

	public User(String firstname, String lastname, String description) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.description = description;
	}

	public User() {
		super();
	}

}
