package com.nnk.springboot.ControllerIT;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebAppConfiguration
@Transactional
public class RegistrationControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
    }

    @Test
    @WithMockUser()
    public void registrationFormTest() throws Exception{
        mockMvc.perform(get("/registrationForm")).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void registrationTest() throws Exception{
        mockMvc.perform(post("/registration").accept(MediaType.TEXT_HTML)
                .param("fullname", "test" )
                .param("username", "test")
                .param("password", "Passw0rd!")
                .param("role", "test")).andExpect(view().name("redirect:app/login"));
    }

    @Test
    @WithMockUser()
    public void registrationTest_UsernameAlreadyUseError() throws Exception{
        mockMvc.perform(post("/registration").accept(MediaType.TEXT_HTML)
                .param("fullname", "test" )
                .param("username", "user")
                .param("password", "Passw0rd!")
                .param("role", "test")).andExpect(view().name("registration"));
    }
}
