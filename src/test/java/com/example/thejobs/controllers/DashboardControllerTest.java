package com.example.thejobs.controllers;

import static org.mockito.Mockito.when;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.services.DashboardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {DashboardController.class})
@ExtendWith(SpringExtension.class)
class DashboardControllerTest {
    @Autowired
    private DashboardController dashboardController;

    @MockBean
    private DashboardService dashboardService;


    @Test
    void testGetConsultantDetails() throws Exception {
        when(dashboardService.getDashboardAnalytics(Mockito.<String>any())).thenReturn(new ResponsePayload());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/dashboard/get-consultant-analytics")
                .param("id", "foo");
        MockMvcBuilders.standaloneSetup(dashboardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }

    @Test
    void testGetMainAnalytics() throws Exception {
        when(dashboardService.getMainAnalytics()).thenReturn(new ResponsePayload());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/dashboard/get-main-analytics");
        MockMvcBuilders.standaloneSetup(dashboardController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }
}

