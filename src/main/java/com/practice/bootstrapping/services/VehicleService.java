package com.practice.bootstrapping.services;

import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public TreeMap<Integer, String> mapOfVehicleIdAndIdentity() {
        return vehicleRepository.findAll()
                .stream()
                .collect(Collectors
                        .toMap(Vehicle::getId, Vehicle::getVehicleName,
                                (oldVehicle, newVehicle) -> oldVehicle,
                                TreeMap::new));
    }

    public Vehicle save(Vehicle entity) {
        return vehicleRepository.save(entity);
    }

    public Optional<Vehicle> findById(Integer id) {
        return vehicleRepository.findById(id);
    }

    public boolean existsById(Integer id) {
        return vehicleRepository.existsById(id);
    }

    public long count() {
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

    public Iterable<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }
}
