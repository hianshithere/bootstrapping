package com.practice.bootstrapping.data_setup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DataSetupRunner implements CommandLineRunner {

    private final VehicleDataLoaderService dataLoaderService;

    public DataSetupRunner(VehicleDataLoaderService dataLoaderService) {
        this.dataLoaderService = dataLoaderService;
    }

    @Override
    public void run(String... args) {
        dataLoaderService.loadVehiclesData("src/main/resources/vehicles.json");
    }
}
hianshithere/bootstrapping/src/main/java/com/practice/bootstrapping/data_setup/DataSetupRunner.java