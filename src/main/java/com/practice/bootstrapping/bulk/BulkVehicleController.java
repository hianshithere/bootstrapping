package com.practice.bootstrapping.bulk;

import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.bootstrapping.bulk.model.BulkModel;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;
import com.practice.bootstrapping.wrapper.BootstrapResponse;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(path = "/vehicle-bulk")
@Slf4j
public class BulkVehicleController {

	private static final String DESCRIPTION_PREFIX = "model make ";

	private final BulkVehicleOperationServices services;
	private final VehicleRepository vehicleRepository;

	public BulkVehicleController(BulkVehicleOperationServices services,
								 VehicleRepository vehicleRepository) {
		this.services = Objects.requireNonNull(services, "services");
		this.vehicleRepository = Objects.requireNonNull(vehicleRepository, "vehicleRepository");
	}

	@PostMapping(path = "list")
	public ResponseEntity<BootstrapResponse> bulkUpdateVehicleTable(@RequestBody List<BulkModel> bulkModelList) {
		if (bulkModelList == null) {
			log.debug("Received null bulkModelList");
			return ResponseEntity.badRequest().body(new BootstrapResponse(null, "Request body must not be null"));
		}

		log.info("Received bulk update request with {} items", bulkModelList.size());

		services.bulkUpdateVehicles(bulkModelList);

		BootstrapResponse payload = new BootstrapResponse(null, "Total Data Size :: " + bulkModelList.size());
		return ResponseEntity.ok(payload);
	}

	@PostMapping(path = "one")
	public ResponseEntity<BootstrapResponse> singleUpdateOnVehicle(@RequestBody BulkModel request) {
		if (request == null || request.getMakeName() == null || request.getMakeName().isBlank()) {
			log.debug("Invalid single update request: {}", request);
			return ResponseEntity.badRequest().body(new BootstrapResponse(null, "Invalid request"));
		}

		Vehicle vehicle = new Vehicle(request.getMakeName(), DESCRIPTION_PREFIX + request.getMakeName());
		vehicleRepository.save(vehicle);

		BootstrapResponse resp = new BootstrapResponse("Data", request);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

}
