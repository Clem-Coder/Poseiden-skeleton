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
public class RatingControllerIT {

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
    public void ratingListTest() throws Exception{
        mockMvc.perform(get("/rating/list").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addRatingFormTest() throws Exception{
        mockMvc.perform(get("/rating/add").session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void addRatingTest() throws Exception{
        mockMvc.perform(post("/rating/validate").session(session).accept(MediaType.TEXT_HTML)
                .param("moodysRating", "test" )
                .param("sandPRating", "test")
                .param("fitchRating", "test")
                .param("orderNumber", "1")).andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser()
    public void addRatingTest_moodysRatingError() throws Exception{
        mockMvc.perform(post("/rating/validate").session(session).accept(MediaType.TEXT_HTML)
                        .param("moodysRating", "" ))
                .andExpect(view().name("rating/add"));
    }

    @Test
    @WithMockUser()
    public void updateRatingFormTest() throws Exception{
        mockMvc.perform(get("/rating/update/{id}", 1).session(session)).andExpect(status().isOk());
    }

    @Test
    @WithMockUser()
    public void updateRatingTest() throws Exception{
        mockMvc.perform(post("/rating/update/{id}", 1).session(session).accept(MediaType.TEXT_HTML)
                .param("moodysRating", "test" )
                .param("sandPRating", "test")
                .param("fitchRating", "test")
                .param("orderNumber", "1")).andExpect(view().name("redirect:/rating/list"));
    }

    @Test
    @WithMockUser()
    public void updateRatingTest_moodysRatingError() throws Exception{
        mockMvc.perform(post("/rating/update/{id}", 1).session(session).accept(MediaType.TEXT_HTML)
                        .param("moodysRating", "" ))
                .andExpect(view().name("rating/update"));
    }

    @Test
    @WithMockUser()
    public void deleteRatingTest() throws Exception{
        mockMvc.perform(get("/rating/delete/{id}", 1).session(session))
                .andExpect(view().name("redirect:/rating/list"));
    }
}
