package com.practice.bootstrapping.bulk;

import com.practice.bootstrapping.bulk.model.BulkModel;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;
import com.practice.bootstrapping.wrapper.BootstrapResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/vehicle-bulk")
public class BulkVehicleController {

	private final BulkVehicleOperationServices services;
	private final VehicleRepository vehicleRepository;

	public BulkVehicleController(BulkVehicleOperationServices services,
								 VehicleRepository vehicleRepository) {
		this.services = services;
		this.vehicleRepository = vehicleRepository;
	}

	@PostMapping(path = "list")
	public ResponseEntity<BootstrapResponse> bulkUpdateVehicleTable(@RequestBody List<BulkModel> bulkModelList) {
		ResponseEntity<BootstrapResponse> entity = new ResponseEntity<BootstrapResponse>(
				new BootstrapResponse(null, "Total Data Size :: " + bulkModelList.size()), HttpStatus.OK);

		services.bulkUpdateDataInVehicleTable(bulkModelList);
		return entity;
	}

	@PostMapping(path = "one")
	public ResponseEntity<BootstrapResponse> singleUpdateOnVehicle(@RequestBody BulkModel entity) {
		String description = "model make ";
		ResponseEntity<BootstrapResponse> resp = new ResponseEntity<BootstrapResponse>(
				new BootstrapResponse("Data", entity), HttpStatus.OK);
		vehicleRepository.save(new Vehicle(entity.getMakeName(), description + entity.getMakeName()));
		return resp;
	}

}
