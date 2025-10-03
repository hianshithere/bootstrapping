package com.practice.bootstrapping.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;
import com.practice.bootstrapping.wrapper.BootstrapResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final ObjectMapper objectMapper;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
        this.objectMapper = new ObjectMapper();
    }

    // Existing method renamed for clarity
    public TreeMap<Integer, String> getVehicleIdAndIdentityMap() {
        return vehicleRepository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        Vehicle::getId,
                        Vehicle::getVehicleName,
                        (oldValue, newValue) -> oldValue,
                        TreeMap::new));
    }

    public Vehicle save(Vehicle entity) {
        return vehicleRepository.save(entity);
    }

    public Optional<Vehicle> findVehicleById(Integer id) {
        return vehicleRepository.findById(id);
    }

    public boolean existsById(Integer id) {
        return vehicleRepository.existsById(id);
    }

    public long countVehicles() {
        return vehicleRepository.count();
    }

    public void deleteById(Integer id) {
        vehicleRepository.deleteById(id);
    }

    public void delete(Vehicle entity) {
        vehicleRepository.delete(entity);
    }

    public void deleteAllById(Iterable<Integer> ids) {
        vehicleRepository.deleteAllById(ids);
    }

    public List<Vehicle> findAllVehicles() {
        Iterable<Vehicle> iterable = vehicleRepository.findAll();
        List<Vehicle> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public Map<Integer, String> getVehicleOptions() {
        return findAllVehicles()
                .stream()
                .collect(Collectors.toMap(Vehicle::getId, Vehicle::getVehicleName));
    }

    public void deleteAllVehicles() {
        vehicleRepository.deleteAll();
    }

    public List<Vehicle> findVehiclesByIds(List<Integer> ids) {
        return vehicleRepository.findAllById(ids)
                .stream()
                .collect(Collectors.toList());
    }

    public List<Vehicle> processVehicleRequest(String request) throws JsonProcessingException {
        BootstrapResponse response = objectMapper.readValue(request, BootstrapResponse.class);
        Object result = response.getResult();
        List<Vehicle> vehicles = new ArrayList<>();
        if(result instanceof List) {
            List<?> list = (List<?>) result;
            for(Object obj : list) {
                Vehicle vehicle = objectMapper.convertValue(obj, Vehicle.class);
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }

    public BootstrapResponse compareJsonData(BootstrapResponse bootstrapResponse) throws JsonProcessingException {
        String jsonString1 = (String) bootstrapResponse.getMetadata();
        String jsonString2 = (String) bootstrapResponse.getResult();

        JsonNode json1 = objectMapper.readTree(jsonString1);
        JsonNode json2 = objectMapper.readTree(jsonString2);

        boolean areTheyEqual = json1.equals(json2);
        Map<String, Object> resultMap = new TreeMap<>();
        resultMap.put("jsonString1", json1);
        resultMap.put("jsonString2", json2);
        resultMap.put("areTheyEqual", areTheyEqual);

        List<Object> resultList = new ArrayList<>();
        resultList.add(resultMap);

        return new BootstrapResponse(resultList, HttpStatus.ACCEPTED);
    }

    // New method for paginated vehicles
    public Page<Vehicle> findPaginatedVehicles(PageRequest pageRequest) {
        return vehicleRepository.findAll(pageRequest);
    }

}
