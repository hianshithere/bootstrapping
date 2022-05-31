package com.practice.bootstrapping.controllers;

import com.practice.bootstrapping.aop.UserResponseReady;
import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.services.UserService;
import com.practice.bootstrapping.wrapper.BootstrapResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
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
	@UserResponseReady
	public Object test() {

		User user = new User ();
		Vehicle vehicle = new Vehicle ();
		vehicle.setId (1);
		vehicle.setVehicleName ("Maruti");
		user.getCollectionOfVehicle ().add (vehicle);
		return user;
	}

}
