package com.practice.bootstrapping.controllers;

import com.practice.bootstrapping.entity.User;
import com.practice.bootstrapping.repositories.UserRepository;
import com.practice.bootstrapping.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test
    public void get_api() throws Exception {
        List<User> usersList = new ArrayList<> ();
        when (userService.findAll ()).thenReturn (usersList);
//                .thenAnswer (i -> i.getArgument (0));

        ResultActions resultActions = mockMvc.perform (
                MockMvcRequestBuilders
                        .get ("/user")
                        .contentType (MediaType.APPLICATION_JSON)
        ).andExpect (status ().isOk ());

        MvcResult mvcResult = resultActions.andReturn ();
        MockHttpServletResponse response = mvcResult.getResponse ();
        String contentAsString = response.getContentAsString ();

        System.out.println (contentAsString);

    }


}
