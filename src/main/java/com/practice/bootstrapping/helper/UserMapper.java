package com.practice.bootstrapping.helper;

import com.practice.bootstrapping.dto.UserDTO;
import com.practice.bootstrapping.entity.User;

public class UserMapper {

    public static UserDTO mapUserEntityToUserDTO(User currentUser) {
        return UserDTO.builder()
                .userId(currentUser.getId())
                .firstname(currentUser.getFirstname())
                .lastname(currentUser.getLastname())
                .userDescription(currentUser.getDescription())
                .userVehicles(currentUser.getCollectionOfVehicle()).get();
    }
}
