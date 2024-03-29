package com.practice.bootstrapping.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.exception.NoDataFoundInBootstrapResponse;
import com.practice.bootstrapping.repositories.VehicleRepository;
import com.practice.bootstrapping.services.VehicleService;
import com.practice.bootstrapping.wrapper.BootstrapResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/vehicle")
public class VehicleController {

	private final VehicleService vehicleService;
	private final VehicleRepository vehicleRepository;

	public VehicleController(VehicleService vehicleService, VehicleRepository vehicleRepository) {
		this.vehicleService = vehicleService;
		this.vehicleRepository = vehicleRepository;
	}

	@GetMapping
	public BootstrapResponse getVehicles() {
		return new BootstrapResponse(vehicleService.mapOfVehicleIdAndIdentity()
				, vehicleService);

	}

	@GetMapping(path = "all")
	public Object findAll() {
		return vehicleRepository.findAll();
	}

	@RequestMapping(path = "options", method = RequestMethod.OPTIONS)
	public Map<Integer, String> findAllOptions() {
		return vehicleRepository.findAll()
				.stream()
				.collect(Collectors
						.toMap(Vehicle::getId, Vehicle::getVehicleName));
	}

	@DeleteMapping
	public ResponseEntity<BootstrapResponse> deleteAll() {
		vehicleRepository.deleteAll();
		return new ResponseEntity<BootstrapResponse>(
				new BootstrapResponse("completed", "deleted all data"),
				HttpStatus.OK);
	}

	@GetMapping(path = "count")
	public ResponseEntity<BootstrapResponse> count() {
		return new ResponseEntity<BootstrapResponse>(
				new BootstrapResponse(HttpStatus.OK, vehicleRepository.count()),
				HttpStatus.OK);
	}

	@PostMapping(path = "findById")
	public ResponseEntity<BootstrapResponse> findById(@RequestParam(name = "id") Integer Id) {
		return new ResponseEntity<BootstrapResponse>(
				new BootstrapResponse(HttpStatus.OK, vehicleRepository.findById(Id)),
				HttpStatus.OK);
	}

	@PostMapping(path = "findByIds", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BootstrapResponse> findByIds(@RequestBody List<Integer> Ids) {
		return new ResponseEntity<BootstrapResponse>(
				new BootstrapResponse(HttpStatus.OK, vehicleRepository.findAllById(Ids)),
				HttpStatus.OK);
	}

	@PostMapping(path = "process")
	public Object extractFromResult(@RequestBody String request) {
		BootstrapResponse readValue = null;
		try {
			readValue = new ObjectMapper().readValue(request, BootstrapResponse.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		List<Vehicle> vechileList = (List<Vehicle>) readValue.getResult();
		return vechileList;
	}

	@PostMapping(path = "comparing")
	public Object comparingTwoJSON(@RequestBody BootstrapResponse bootstrapResponse)
			throws Exception, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		String jsonString1 = (String) bootstrapResponse.getMetadata();
		String jsonString2 = (String) bootstrapResponse.getResult();

		JsonNode json1 = mapper.readTree(jsonString1);
		JsonNode json2 = mapper.readTree(jsonString2);

		boolean areTheyEqual = json1.equals(json2);

		TreeMap<String, Object> map = new TreeMap<String, Object>();

		map.put("jsonString1", json1);
		map.put("jsonString2", json2);
		map.put("areTheyEqual", areTheyEqual);
		List<Object> returnMapAsList = new ArrayList<Object>() {{
				add(map);
		}};

		return new BootstrapResponse(returnMapAsList, HttpStatus.ACCEPTED);
	}

	@GetMapping("exception")
	public BootstrapResponse throwSomeException() {
		throw new NoDataFoundInBootstrapResponse("Jaan boochke feka hua exception");
	}

	@GetMapping("test")
	public BootstrapResponse test() {
		Page<Vehicle> vehiclePage = vehicleRepository.findAll(PageRequest.ofSize(10));
		return new BootstrapResponse(vehiclePage.getContent(), vehiclePage.getSize());
	}

}
