package com.practice.bootstrapping.dto;

import com.practice.bootstrapping.aop.BootstrapValidate;
import lombok.Data;

@Data
public class UserDTO {

    public String valid_username;

    @BootstrapValidate
    public String valid_userId;

}
