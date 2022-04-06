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
public class TradeControllerIT {
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
    public void tradeListTest() throws Exception{
        mockMvc.perform(get("/trade/list").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addTradeFormTest() throws Exception{
        mockMvc.perform(get("/trade/add").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addTradeTest() throws Exception{
        mockMvc.perform(post("/trade/validate").session(session).accept(MediaType.TEXT_HTML)
                .param("account", "test" )
                .param("type", "test")
                .param("buyQuantity", "0.1")).andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser()
    public void addTradeTest_accountError() throws Exception{
        mockMvc.perform(post("/trade/validate").session(session).accept(MediaType.TEXT_HTML)
                        .param("account", "" ))
                .andExpect(view().name("trade/add"));
    }

    @Test
    @WithMockUser()
    public void updateTradeFormTest() throws Exception{
        mockMvc.perform(get("/trade/update/{id}", 1).session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void updateTradeTest() throws Exception{
        mockMvc.perform(post("/trade/update/{id}", 1).session(session).accept(MediaType.TEXT_HTML)
                .param("account", "test" )
                .param("type", "test")
                .param("buyQuantity", "0.1")).andExpect(view().name("redirect:/trade/list"));
    }

    @Test
    @WithMockUser()
    public void updateTradeTest_accountError() throws Exception{
        mockMvc.perform(post("/trade/update/{id}", 1).session(session).accept(MediaType.TEXT_HTML)
                        .param("account", "" ))
                .andExpect(view().name("trade/update"));
    }

    @Test
    @WithMockUser()
    public void deleteTradeTest() throws Exception{
        mockMvc.perform(get("/trade/delete/{id}", 1).session(session))
                .andExpect(view().name("redirect:/trade/list"));
    }
}
