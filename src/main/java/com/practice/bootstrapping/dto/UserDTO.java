package com.practice.bootstrapping.dto;

import com.practice.bootstrapping.entity.Vehicle;
import lombok.Builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Builder(buildMethodName = "get")
public class UserDTO {

    private Integer userId;
    private String firstname;
    private String lastname;
    private String userDescription;
    private Collection<Vehicle> userVehicles = new ArrayList<Vehicle>();

    public UserDTO id(int id) {
        this.userId = id;
        return this;
    }

    public UserDTO firstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public UserDTO lastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

    public UserDTO userDescription(String userDescription) {
        this.userDescription = userDescription;
        return this;
    }

    public UserDTO collectionOfVehicles(Collection collectionOfVehicle) {
        this.userVehicles = collectionOfVehicle;
        return this;
    }

    public Collection getUserVehicles() {
        if(this.userVehicles.isEmpty())
            return Collections.EMPTY_LIST;
        return this.userVehicles;
    }

}
