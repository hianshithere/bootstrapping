package com.practice.bootstrapping.data_setup;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.practice.bootstrapping.bulk.model.BulkModel;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.TypeFactory.defaultInstance;

@Configuration
@Slf4j
public class DataSetupRunner implements CommandLineRunner {

    private final VehicleRepository vehicleRepository;
    private final ObjectMapper objectMapper;

    public DataSetupRunner(VehicleRepository vehicleRepository, ObjectMapper objectMapper) {
        this.vehicleRepository = vehicleRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) {
        if (vehicleRepository.count() > 0) {
            return;
        }

        String readString = "";
        try {
            readString = Files.readString(Path.of("src/main/resources/vehicles.json"));
            if (readString.isBlank()) {
                throw new NoSuchFileException("vehciles.json");
            }
            List<BulkModel> bulkModels = objectMapper.readValue(readString, new TypeReference<List<BulkModel>>() {});
            List<Vehicle> vehicles = bulkModels.stream()
                    .map(model -> {
                        return new Vehicle(model.getMakeName(), "THIS IS " + model.getMakeName());
                    })
                    .collect(Collectors.toList());

            vehicleRepository.saveAll(vehicles);
            log.info("Loaded {} vehicles into the repository", vehicles.size());
        } catch (Exception e) {
            log.error("Error loading vehicles from file: {}", e.getMessage());
        }
    }
}

