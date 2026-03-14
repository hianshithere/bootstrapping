package com.practice.bootstrapping.bulk;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.practice.bootstrapping.bulk.model.BulkModel;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for bulk operations on the {@link Vehicle} table.
 * <p>
 * Refactor goals: validate inputs, avoid NPEs, log structured messages, and
 * keep the method small and testable.
 */
@Service
@Slf4j
public class BulkVehicleOperationServices {

	private static final String DESCRIPTION_PREFIX = "model make ";

	private final VehicleRepository vehicleRepository;

	public BulkVehicleOperationServices(VehicleRepository vehicleRepository) {
		this.vehicleRepository = Objects.requireNonNull(vehicleRepository, "vehicleRepository");
	}

	/**
	 * Asynchronously processes the provided list of {@link BulkModel} and saves any
	 * constructed {@link Vehicle} entities.
	 *
	 * @param bulkModelList list of models to process; if null or empty the method returns immediately
	 */
	@Async
	public void bulkUpdateVehicles(List<BulkModel> bulkModelList) {
		if (bulkModelList == null || bulkModelList.isEmpty()) {
			log.debug("No bulk models provided - skipping vehicle update");
			return;
		}

		List<Vehicle> vehicles = bulkModelList.stream()
				.filter(Objects::nonNull)
				.map(BulkModel::getMakeName)
				.filter(make -> make != null && !make.isBlank())
				.map(make -> new Vehicle(make, DESCRIPTION_PREFIX + make))
				.collect(Collectors.toList());

		if (vehicles.isEmpty()) {
			log.debug("No valid vehicles to save after filtering");
			return;
		}

		vehicleRepository.saveAll(vehicles);

		// Sleep only for demo/coordination purposes; restore interrupt status if interrupted.
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			log.warn("Bulk vehicle update interrupted", e);
		}

		log.info("Bulk vehicle update completed - saved {} vehicles", vehicles.size());
	}
}
