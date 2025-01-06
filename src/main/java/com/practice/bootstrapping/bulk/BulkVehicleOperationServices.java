package com.practice.bootstrapping.bulk;

import com.practice.bootstrapping.bulk.model.BulkModel;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BulkVehicleOperationServices {

  private final VehicleRepository vehicleRepository;

  public BulkVehicleOperationServices(VehicleRepository vehicleRepository) {
    this.vehicleRepository = vehicleRepository;
  }

  @Async
  public void bulkUpdateDataInVehicleTable(List<BulkModel> bulkModelList) {
    String description = "model make ";
    List<Vehicle> vehicles =
        bulkModelList.stream()
            .filter(vehicle -> Objects.nonNull(vehicle.getMakeName()))
            .map(
                bulkModel ->
                    new Vehicle(bulkModel.getMakeName(), description + bulkModel.getMakeName()))
            .collect(Collectors.toList());

    vehicleRepository.saveAll(vehicles);
	log.info("bulk update for vehicle data is completed");
  }
}
