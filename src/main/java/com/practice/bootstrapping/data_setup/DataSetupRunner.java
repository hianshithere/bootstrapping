package com.practice.bootstrapping.data_setup;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * Initializes vehicle data after the application is fully ready.
 * Delegates to VehicleDataLoaderService to avoid code duplication.
 * Runs asynchronously to avoid blocking the web server during startup.
 */
@Component
@Slf4j
public class DataSetupRunner {

    private static final String VEHICLES_JSON_PATH = "src/main/resources/vehicles.json";

    private final VehicleDataLoaderService vehicleDataLoaderService;

    public DataSetupRunner(VehicleDataLoaderService vehicleDataLoaderService) {
        this.vehicleDataLoaderService = vehicleDataLoaderService;
    }

    /**
     * Triggered after the application is fully started.
     * Loads initial vehicle data asynchronously in a separate thread.
     */
    @EventListener(ApplicationReadyEvent.class)
    @Async
    public void initializeVehicleData(ApplicationReadyEvent event) {
        log.info("Starting vehicle data initialization (async).");
        try {
            vehicleDataLoaderService.loadVehiclesData(VEHICLES_JSON_PATH);
            log.info("Vehicle data initialization completed successfully.");
        } catch (Exception e) {
            log.error("Error during vehicle data initialization: {}", e.getMessage(), e);
        }
    }
}