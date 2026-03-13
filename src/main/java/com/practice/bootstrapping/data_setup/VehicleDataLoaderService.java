package com.practice.bootstrapping.data_setup;

import static com.fasterxml.jackson.databind.type.TypeFactory.defaultInstance;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.practice.bootstrapping.bulk.model.BulkModel;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service responsible for loading and persisting vehicle data from JSON files.
 * Provides reusable logic for vehicle data initialization across the application.
 */
@Slf4j
@Service
public class VehicleDataLoaderService {

    private static final String VEHICLE_DESCRIPTION_TEMPLATE = "THIS IS %s";

    private final VehicleRepository vehicleRepository;
    private final ObjectMapper objectMapper;

    public VehicleDataLoaderService(VehicleRepository vehicleRepository, ObjectMapper objectMapper) {
        this.vehicleRepository = vehicleRepository;
        this.objectMapper = objectMapper;
    }

    /**
     * Loads vehicle data from a JSON file and persists it to the repository.
     * Skips loading if vehicles already exist in the database.
     *
     * @param filePath the path to the JSON file containing vehicle data
     */
    public void loadVehiclesData(String filePath) {
        try {
            if (vehicleRepository.count() > 0) {
                log.info("Vehicles already loaded, skipping data load.");
                return;
            }

            String jsonContent = Files.readString(Path.of(filePath));
            if (jsonContent.isBlank()) {
                throw new NoSuchFileException(filePath + " is blank or empty.");
            }

            List<Vehicle> vehicles = parseAndMapVehicles(jsonContent);
            vehicleRepository.saveAll(vehicles);
            log.info("Persisted {} vehicles to the database.", vehicles.size());

        } catch (NoSuchFileException e) {
            log.warn("Vehicle data file not found or empty: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Error while loading vehicle data: {}", e.getMessage(), e);
        }
    }

    /**
     * Parses JSON content and maps bulk models to Vehicle entities.
     * Extracted for reusability and testability.
     *
     * @param jsonContent the JSON string containing vehicle data
     * @return a list of Vehicle entities ready for persistence
     * @throws Exception if JSON parsing or mapping fails
     */
    private List<Vehicle> parseAndMapVehicles(String jsonContent) throws Exception {
        CollectionType vehicleCollectionType = defaultInstance()
                .constructCollectionType(List.class, BulkModel.class);

        List<BulkModel> bulkModels = objectMapper.readValue(jsonContent, vehicleCollectionType);

        return bulkModels.stream()
                .map(model -> new Vehicle(
                        model.getMakeName(),
                        String.format(VEHICLE_DESCRIPTION_TEMPLATE, model.getMakeName())
                ))
                .collect(Collectors.toList());
    }
}
