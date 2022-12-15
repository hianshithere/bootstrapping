package com.practice.bootstrapping.controllers;

import com.practice.bootstrapping.aop.UserResponseReady;
import com.practice.bootstrapping.dto.UserDTO;
import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.services.UserService;
import com.practice.bootstrapping.wrapper.BootstrapResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BootstrapResponse findAll() {
        return new BootstrapResponse (userService.findAll (), "userService");
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public User findById(@RequestBody User user) {
        Optional<User> findById = userService.findById (user.getId ());
        if (findById.isPresent ())
            return findById.get ();

        return new User (null, null, "NOT FOUND");
    }

    @GetMapping(path = "/test", produces = APPLICATION_JSON_VALUE)
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

    @PostMapping(value = "/dto", consumes = APPLICATION_JSON_VALUE)
    @UserResponseReady
    public Object dto(@Valid @RequestBody UserDTO userDTO) {
        return userDTO;
    }
}
