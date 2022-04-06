package com.nnk.springboot.ControllerIT;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.controllers.LoginController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
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

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
public class BidListControllerIT {

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
    public void bidListTest() throws Exception{
        mockMvc.perform(get("/bidList/list").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addBidFormTest() throws Exception{
        mockMvc.perform(get("/bidList/add").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addBidTest() throws Exception{
        mockMvc.perform(post("/bidList/validate").session(session).accept(MediaType.TEXT_HTML)
                .param("account", "test" )
                .param("type", "test")
                .param("bidQuantity", "10")).andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser()
    public void addBidTest_MandatoryAccountError() throws Exception{
        mockMvc.perform(post("/bidList/validate").session(session).accept(MediaType.TEXT_HTML)
                        .param("account", "" ))
                .andExpect(view().name("bidList/add"));
    }

    @Test
    @WithMockUser()
    public void updateBidFormTest() throws Exception{
        mockMvc.perform(get("/bidList/update/{id}", 1).session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void updateBidTest() throws Exception{
        mockMvc.perform(post("/bidList/update/{id}", 1).session(session).accept(MediaType.TEXT_HTML)
                .param("account", "test" )
                .param("type", "test")
                .param("bidQuantity", "10")).andExpect(view().name("redirect:/bidList/list"));
    }

    @Test
    @WithMockUser()
    public void updateBidTest_MandatoryAccountError() throws Exception{
        mockMvc.perform(post("/bidList/update/{id}", 1).session(session).accept(MediaType.TEXT_HTML)
                .param("account", "" ))
                .andExpect(view().name("bidList/update"));
    }

    @Test
    @WithMockUser()
    public void deleteBidTest() throws Exception{
        mockMvc.perform(get("/bidList/delete/{id}", 1).session(session))
                .andExpect(view().name("redirect:/bidList/list"));
    }



}
