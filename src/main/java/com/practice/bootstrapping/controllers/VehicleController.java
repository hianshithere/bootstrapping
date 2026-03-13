package com.practice.bootstrapping.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.exception.NoDataFoundInBootstrapResponse;
import com.practice.bootstrapping.services.VehicleService;
import com.practice.bootstrapping.wrapper.BootstrapResponse;

@RestController
@RequestMapping(path = "/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // Delegate business logic to service
    @GetMapping
    public BootstrapResponse getVehicles() {
        return new BootstrapResponse(vehicleService.getVehicleIdAndIdentityMap(), vehicleService);
    }

    @GetMapping(path = "/all")
    public ResponseEntity<?> findAll() {
        List<Vehicle> vehicles = vehicleService.findAllVehicles();
        return ResponseEntity.ok(vehicles);
    }

    @RequestMapping(path = "/options", method = RequestMethod.OPTIONS)
    public ResponseEntity<Map<Integer, String>> findAllOptions() {
        Map<Integer, String> options = vehicleService.getVehicleOptions();
        return ResponseEntity.ok(options);
    }

    @DeleteMapping
    public ResponseEntity<BootstrapResponse> deleteAll() {
        vehicleService.deleteAllVehicles();
        return ResponseEntity.ok(new BootstrapResponse("completed", "deleted all data"));
    }

    @GetMapping(path = "/count")
    public ResponseEntity<BootstrapResponse> count() {
        long count = vehicleService.countVehicles();
        return ResponseEntity.ok(new BootstrapResponse(HttpStatus.OK, count));
    }

    @PostMapping(path = "/findById")
    public ResponseEntity<BootstrapResponse> findById(@RequestParam(name = "id") Integer id) {
        Object result = vehicleService.findVehicleById(id);
        return ResponseEntity.ok(new BootstrapResponse(HttpStatus.OK, result));
    }

    @PostMapping(path = "/findByIds", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BootstrapResponse> findByIds(@RequestBody List<Integer> ids) {
        Object result = vehicleService.findVehiclesByIds(ids);
        return ResponseEntity.ok(new BootstrapResponse(HttpStatus.OK, result));
    }

    @PostMapping(path = "/process")
    public ResponseEntity<?> processRequest(@RequestBody String request) {
        // Let the service process the input request
        try {
            List<Vehicle> vehicles = vehicleService.processVehicleRequest(request);
            return ResponseEntity.ok(vehicles);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping(path = "/comparing")
    public ResponseEntity<BootstrapResponse> comparingTwoJSON(@RequestBody BootstrapResponse bootstrapResponse) throws Exception {
        BootstrapResponse response = vehicleService.compareJsonData(bootstrapResponse);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/exception")
    public BootstrapResponse throwSomeException() {
        throw new NoDataFoundInBootstrapResponse("Jaan boochke feka hua exception");
    }

    @GetMapping("/test")
    public BootstrapResponse test() {
        Page<Vehicle> vehiclePage = vehicleService.findPaginatedVehicles(PageRequest.ofSize(10));
        return new BootstrapResponse(vehiclePage.getContent(), vehiclePage.getSize());
    }

}
