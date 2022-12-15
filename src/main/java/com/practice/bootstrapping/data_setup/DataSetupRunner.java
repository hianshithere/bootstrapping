package com.practice.bootstrapping.data_setup;

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

    VehicleRepository vehicleRepository;

    public DataSetupRunner(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (vehicleRepository.count() > 0) return;
        String readString = "";
        try {
            readString = Files.readString(Path.of("src/main/resources/vehicles.json"));
            if (readString.isBlank()) throw new NoSuchFileException("vehciles.json");
            dataloader(readString);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info(String.valueOf(readString.length()));
    }

    private void dataloader(String readString) {

        CollectionType typeReference = defaultInstance()
                .constructCollectionType(List.class, BulkModel.class);

        ObjectMapper mapper = new ObjectMapper();

        try {
            List<BulkModel> bulkModelList = mapper.readValue(readString, typeReference);
            List<Vehicle> vehicles = bulkModelList.stream()
                    .map(model -> {
                        return new Vehicle(model.getMakeName(), "THIS IS " + model.getMakeName());
                    })
                    .collect(Collectors.toList());

            vehicleRepository.saveAll(vehicles);
            log.info(String.valueOf(vehicles.size()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
