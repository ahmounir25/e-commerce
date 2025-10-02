package com.newProject.first.controller;

import com.newProject.first.DAO.userRepository;
import com.newProject.first.DTO.LoginResponse;
import com.newProject.first.entity.User;
import com.newProject.first.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.JsonPath;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class userControllerTest {
    private userRepository userRepository;
    private JwtService jwtService;
    private MockMvc mockMvc;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public userControllerTest(userRepository userRepository, JwtService jwtService,
                              MockMvc mockMvc, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.mockMvc = mockMvc;
        this.passwordEncoder = passwordEncoder;
    }

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    public void registerTest() throws Exception {
        // bad email
        mockMvc.perform(post("/register").
                contentType(MediaType.APPLICATION_JSON).
                content("""
                        {
                        "firstName":"abdo",
                        "lastName":"ehab",
                        "email":"abdo@gmail",
                        "password":"123456"
                        }
                        """)).andExpect(status().isBadRequest());
        //  bad name
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content("""
                {
                "firstName":"   ",
                "lastName":"",
                "email":"abdo@gmail.com",
                "password":"123456"
                }
                """)).andExpect(status().isBadRequest());
        // bad pass
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content("""
                {
                "firstName":"abdo",
                "lastName":"ehab",
                "email":"abdo@gmail.com",
                "password":"12345"
                }
                """)).andExpect(status().isBadRequest());
        // success
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content("""
                {
                "firstName":"abdo",
                "lastName":"ehab",
                "email":"abdo@gmail.com",
                "password":"123456"
                }
                """)).andExpect(status().isCreated());

    }

    @Test
    public void loginTest() throws Exception {
        // creating user
        userRepository.save(new User("abdo", "ehab", "abdo@gmail.com",
                passwordEncoder.encode("123456")));

        // bad email
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                {
                                "email":"abdo@.com",
                                "password":"123456"
                                }
                                """
                )).andExpect(status().isBadRequest());

        // bad pass
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                {
                                "email":"abdo@gmail.com",
                                "password":"123"
                                }
                                """
                )).andExpect(status().isBadRequest());

        // not existing user
        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                                {
                                "email":"test33@gmail.com",
                                "password":"123456"
                                }
                                """
                )).andExpect(status().isUnauthorized());

        // success

        mockMvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                        {
                                        "email":"abdo@gmail.com",
                                        "password":"123456"
                                        }
                                        """
                        )).andExpect(status().isOk()).
                andExpect(jsonPath("$.accessToken").exists()).
                andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    public void refreshTest() throws Exception {
//        userRepository.save(new User("abdo", "ehab", "abdo@gmail.com",
//                passwordEncoder.encode("123456")));
        // bad refresh token
        mockMvc.perform(
                post("/refresh").
                        contentType(MediaType.APPLICATION_JSON).content("""
                                {
                                "refreshToken":"LKioAVJPLkqtkRt5o1M7H73j5qmhpxXjfhjJ0dMBJM="
                                }
                                """)
        ).andExpect(status().isUnauthorized());
        // success
        mockMvc.perform(
                post("/refresh").
                        contentType(MediaType.APPLICATION_JSON).
                        content("""
                                {
                                "refreshToken":"dNRPILAzvCkmeZe91bQCvQhuQ9BD8a9lfh+8cz5ocoU="
                                }
                                """)
        ).andExpect(status().isOk());


    }

    @Test
    public void logoutTest() throws Exception {
        // bad email
        mockMvc.perform(post("/logout").
                contentType(MediaType.APPLICATION_JSON).
                content("""
                        {
                        "email":"fsfgfhg@gmail.com"
                        }
                        """)).andExpect(status().isBadRequest());

        mockMvc.perform(post("/logout").
                contentType(MediaType.APPLICATION_JSON).
                content("""
                        {
                        "email":"abdo@gmail.com"
                        }
                        """)).andExpect(status().isOk());
    }

    @Test
    public void getUserTest() throws Exception {
        User user = userRepository.save(new User("abdo", "ehab", "abdo@gmail.com",
                passwordEncoder.encode("123456")));
        String token = jwtService.generateAccessToken(user.getEmail());

        // authorized user
        mockMvc.perform(get("/user").
                        header("Authorization", "Bearer " + token))
                .andExpect(status().isOk()).
                andExpect(jsonPath("$.firstName").value(user.getFirstName())).
                andExpect(jsonPath("$.lastName").value(user.getLastName())).
                andExpect(jsonPath("$.email").value(user.getEmail())).
                andExpect(jsonPath("$.password").value(user.getPassword()));
        // not valid token
        mockMvc.perform(get("/user").
                        header("Authorization", "Bearer " +
                                "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtb2hhbWVkQGdtYWlsLmNvbSIsImlhdCI6MTc1ODg1NDY3MCwiZXhwIjoxNzU4ODU1NTcwfQ.WtdYOi6yJPl_LWxhi_8LLlbN_imdLLCkcOC4T6sZ_DY"))
                .andExpect(status().isUnauthorized());
    }
}
