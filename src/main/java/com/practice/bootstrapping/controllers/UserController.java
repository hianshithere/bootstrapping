package com.practice.bootstrapping.controllers;

import com.practice.bootstrapping.aop.UserResponseReady;
import com.practice.bootstrapping.dto.UserDTO;
import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.services.UserService;
import com.practice.bootstrapping.wrapper.BootstrapResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    Log log = LogFactory.getLog (UserController.class);
    @Autowired
    private UserService userService;


//	@GetMapping
//	@ResponseStatus(HttpStatus.OK)
//	public List<User> findAll() {
//		return userService.findAll();
//	}

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BootstrapResponse findAll() {
        return new BootstrapResponse (userService.findAll (), "userService");
    }

    @PostMapping
    public User findById(@RequestBody User user) {
        Optional<User> findById = userService.findById (user.getId ());
        if (findById.isPresent ())
            return findById.get ();

        return new User (null, null, "NOT FOUND");
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

    @GetMapping("/validate")
    public Object bootstrapValidateCheck() {
        return new UserDTO ();
    }

    @PostMapping(value = "/dto", consumes = MediaType.APPLICATION_JSON_VALUE)
    @UserResponseReady
    public Object dto(@Valid @RequestBody UserDTO userDTO) {
        return userDTO;
    }
}
