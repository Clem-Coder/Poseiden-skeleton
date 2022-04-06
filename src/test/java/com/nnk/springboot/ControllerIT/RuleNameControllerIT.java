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
public class RuleNameControllerIT {

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
    public void ruleNameListTest() throws Exception{
        mockMvc.perform(get("/ruleName/list").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addRuleNameFormTest() throws Exception{
        mockMvc.perform(get("/ruleName/add").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addRuleNameTest() throws Exception{
        mockMvc.perform(post("/ruleName/validate").session(session).accept(MediaType.TEXT_HTML)
                .param("name", "test" )
                .param("description", "test")
                .param("json", "test")
                .param("template", "test")
                .param("sqlStr", "test")
                .param("sqlPart", "test")).andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser()
    public void addRuleNameTest_nameError() throws Exception{
        mockMvc.perform(post("/ruleName/validate").session(session).accept(MediaType.TEXT_HTML)
                        .param("name", "" ))
                .andExpect(view().name("ruleName/add"));
    }

    @Test
    @WithMockUser()
    public void updateRuleNameFormTest() throws Exception{
        mockMvc.perform(get("/ruleName/update/{id}", 1).session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void updateRuleNameTest() throws Exception{
        mockMvc.perform(post("/ruleName/update/{id}", 1).session(session).accept(MediaType.TEXT_HTML)
                .param("name", "test" )
                .param("description", "test")
                .param("json", "test")
                .param("template", "test")
                .param("sqlStr", "test")
                .param("sqlPart", "test")).andExpect(view().name("redirect:/ruleName/list"));
    }

    @Test
    @WithMockUser()
    public void updateRatingTest_nameError() throws Exception{
        mockMvc.perform(post("/ruleName/update/{id}", 1).session(session).accept(MediaType.TEXT_HTML)
                        .param("name", "" ))
                .andExpect(view().name("ruleName/update"));
    }

    @Test
    @WithMockUser()
    public void deleteRatingTest() throws Exception{
        mockMvc.perform(get("/ruleName/delete/{id}", 1).session(session))
                .andExpect(view().name("redirect:/ruleName/list"));
    }
}
