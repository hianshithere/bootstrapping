package com.practice.bootstrapping.data_setup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.practice.bootstrapping.bulk.model.BulkModel;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.TypeFactory.defaultInstance;

@Slf4j
@Service
public class VehicleDataLoaderService {

    private final VehicleRepository vehicleRepository;
    private final ObjectMapper objectMapper;

    public VehicleDataLoaderService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
        this.objectMapper = new ObjectMapper();
    }

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
            CollectionType typeReference = defaultInstance().constructCollectionType(List.class, BulkModel.class);
            List<BulkModel> bulkModelList = objectMapper.readValue(jsonContent, typeReference);
            List<Vehicle> vehicles = bulkModelList.stream()
                    .map(model -> new Vehicle(model.getMakeName(), "THIS IS " + model.getMakeName()))
                    .collect(Collectors.toList());
            vehicleRepository.saveAll(vehicles);
            log.info("Loaded {} vehicles.", vehicles.size());
        } catch (Exception e) {
            log.error("Error while loading vehicle data: {}", e.getMessage());
        }
    }
}
