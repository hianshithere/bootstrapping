package com.practice.bootstrapping.controllers;

import java.util.Optional;

import com.practice.bootstrapping.aop.UserResponseReady;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.services.UserService;
import com.practice.bootstrapping.wrapper.BootstrapResponse;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@UserResponseReady
	public BootstrapResponse findAll() {
		return new BootstrapResponse(userService.findAll(), "userService");
	}

	
//	@GetMapping
//	@ResponseStatus(HttpStatus.OK)
//	public List<User> findAll() {
//		return userService.findAll();
//	}
	
	@PostMapping
	public User findById(@RequestBody User user) {
		Optional<User> findById = userService.findById(user.getId());
		if (findById.isPresent())
			return findById.get();

		return new User(null, null, "NOT FOUND");
	}

	@GetMapping(path = "/test")
	public Object test() {

		User user = new User();
		Vehicle vehicle = new Vehicle();
		vehicle.setId(1);
		vehicle.setVehicleName("Maruti");
		user.getCollectionOfVehicle().add(vehicle);
		return user;
	}

}
