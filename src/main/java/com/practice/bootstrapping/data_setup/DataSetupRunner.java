package com.practice.bootstrapping.data_setup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.practice.bootstrapping.bulk.model.BulkModel;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.type.TypeFactory.*;

@Configuration
@Slf4j
public class DataSetupRunner implements CommandLineRunner {

    @Autowired
    VehicleRepository vehicleRepository;

    @Override
    public void run(String... args) throws Exception {
        if (vehicleRepository.count() > 0) return;
        String readString = "";
        try {
            readString = Files.readString(Path.of("src/main/resources/vehicles.json"));
            if (readString.isBlank()) throw new NoSuchFileException("vehciles.json");
            dataloader(readString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(readString.length());

    }

    private void dataloader(String readString) {

        CollectionType typeReference = defaultInstance().constructCollectionType
                (List.class, BulkModel.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            List<BulkModel> bulkModelList = mapper.readValue(readString, typeReference);

            List<Vehicle> vehicles = bulkModelList.stream().map(model -> {
                return new Vehicle(model.getMakeName(), "THIS IS " + model.getMakeName());
            }).collect(Collectors.toList());

            vehicleRepository.saveAll(vehicles);

            System.out.println("vehicles loaded to database=" + vehicles.size());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
