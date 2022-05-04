package com.practice.bootstrapping.bulk;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.practice.bootstrapping.bulk.model.BulkModel;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;

@Component
public class BulkVehicleOperationServices {
	
	@Autowired
	VehicleRepository vehicleRepository;

	@Async
	public void bulkUpdateDataInVehicleTable(List<BulkModel> bulkModelList) {

		String description = "model make ";
		List<Vehicle> vehicles = bulkModelList.stream()
				.filter(vehicle -> !vehicle.getMakeName().isBlank() || !vehicle.getMakeName().isEmpty())
				.map(bulkModel -> new Vehicle(bulkModel.getMakeName(), description + bulkModel.getMakeName()))
				.collect(Collectors.toList());

		vehicleRepository.saveAll(vehicles);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("************************************");
		System.out.println("***** VEHICLE UPDATE COMPLETED *****");
		System.out.println("************************************");
	}
}
