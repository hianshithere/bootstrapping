package com.practice.bootstrapping.services;

import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.bootstrapping.entity.Vehicle;
import com.practice.bootstrapping.repositories.VehicleRepository;

@Service
public class VehicleService {

	@Autowired
	private VehicleRepository vehicleRepository;

	public TreeMap<Integer, String> mapOfVehicleIdAndIdentity() {
		TreeMap<Integer, String> mapOfVehicleIdAndIdentity = vehicleRepository.findAll().stream().collect(Collectors
				.toMap(Vehicle::getId, Vehicle::getVehicleName, (oldVehicle, newVehicle) -> oldVehicle, TreeMap::new));
		return mapOfVehicleIdAndIdentity;
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
