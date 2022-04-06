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
public class CurvePointControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockHttpSession session;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity()).build();
        session.setAttribute("userInfo", "username");
    }

    @Test
    @WithMockUser()
    public void curvePointListTest() throws Exception{
        mockMvc.perform(get("/curvePoint/list").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addCurvePointFormTest() throws Exception{
        mockMvc.perform(get("/curvePoint/add").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addCurvePointTest() throws Exception{
        mockMvc.perform(post("/curvePoint/validate").session(session).accept(MediaType.TEXT_HTML)
                .param("curveId", "1" )
                .param("term", "0.1")
                .param("value", "0.1")).andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser()
    public void addCurvePointTest_TermError() throws Exception{
        mockMvc.perform(post("/curvePoint/validate").session(session).accept(MediaType.TEXT_HTML)
                        .param("term", "" ))
                .andExpect(view().name("curvePoint/add"));
    }

    @Test
    @WithMockUser()
    public void updateCurvePointFormTest() throws Exception{
        mockMvc.perform(get("/curvePoint/update/{id}", 1).session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void updateCurvePointTest() throws Exception{
        mockMvc.perform(post("/curvePoint/update/{id}", 1).session(session).accept(MediaType.TEXT_HTML)
                .param("curveId", "1" )
                .param("term", "0.1")
                .param("value", "0.1")).andExpect(view().name("redirect:/curvePoint/list"));
    }

    @Test
    @WithMockUser()
    public void updateCurvePointTest_TermError() throws Exception{
        mockMvc.perform(post("/curvePoint/update/{id}", 1).session(session).accept(MediaType.TEXT_HTML)
                        .param("term", "" ))
                .andExpect(view().name("curvePoint/update"));
    }

    @Test
    @WithMockUser()
    public void deleteCurvePointTest() throws Exception{
        mockMvc.perform(get("/curvePoint/delete/{id}", 1).session(session))
                .andExpect(view().name("redirect:/curvePoint/list"));
    }

}
