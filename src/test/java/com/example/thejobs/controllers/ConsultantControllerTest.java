package com.example.thejobs.controllers;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.thejobs.advice.ResponsePayload;
import com.example.thejobs.dto.consultant.ConsultantDTO;
import com.example.thejobs.dto.enums.Role;
import com.example.thejobs.services.ConsultantService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {ConsultantController.class})
@ExtendWith(SpringExtension.class)
class ConsultantControllerTest {
    @Autowired
    private ConsultantController consultantController;

    @MockBean
    private ConsultantService consultantService;

    @Test
    void testRegisterConsultant2() throws Exception {
        when(consultantService.registerConsultant(Mockito.<ConsultantDTO>any())).thenReturn(new ResponsePayload());

        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setCountry("GB");
        consultantDTO
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantDTO.setEmail("jane.doe@example.org");
        consultantDTO.setFirstName("Jane");
        consultantDTO.setId("42");
        consultantDTO.setJobType("Job Type");
        consultantDTO.setLastName("Doe");
        consultantDTO.setMobile("9999999999");
        consultantDTO.setNic("Nic");
        consultantDTO.setPassword("Navod@2000");
        consultantDTO.setRole(Role.ADMIN);
        consultantDTO.setStatus(true);
        consultantDTO.setTimeSlots(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(consultantDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/consultant/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(consultantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }


    @Test
    void testUpdateConsultant3() throws Exception {
        when(consultantService.updateConsultant(Mockito.<ConsultantDTO>any())).thenReturn(new ResponsePayload());
        java.sql.Date createdDate = mock(java.sql.Date.class);
        when(createdDate.getTime()).thenReturn(10L);

        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setCountry("GB");
        consultantDTO.setCreatedDate(createdDate);
        consultantDTO.setEmail("jane.doe@example.org");
        consultantDTO.setFirstName("Jane");
        consultantDTO.setId("42");
        consultantDTO.setJobType("Job Type");
        consultantDTO.setLastName("Doe");
        consultantDTO.setMobile("9999999999");
        consultantDTO.setNic("Nic");
        consultantDTO.setPassword("iloveyou");
        consultantDTO.setRole(Role.ADMIN);
        consultantDTO.setStatus(true);
        consultantDTO.setTimeSlots(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(consultantDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/consultant/update-consultant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(consultantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }

    @Test
    void testGetAllConsultants() throws Exception {
        when(consultantService.getAllConsultants()).thenReturn(new ResponsePayload());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/consultant/get-all");
        MockMvcBuilders.standaloneSetup(consultantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }

    @Test
    void testGetMyBooking() throws Exception {
        when(consultantService.getMyBooking(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ResponsePayload());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/consultant/get-my-booking")
                .param("id", "foo")
                .param("status", "foo");
        MockMvcBuilders.standaloneSetup(consultantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }

    @Test
    void testDeactivateConsultant() throws Exception {
        when(consultantService.deactivateConsultant(Mockito.<String>any(), anyBoolean())).thenReturn(new ResponsePayload());
        MockHttpServletRequestBuilder paramResult = MockMvcRequestBuilders.put("/api/v1/consultant/activation")
                .param("id", "foo");
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("status", String.valueOf(true));
        MockMvcBuilders.standaloneSetup(consultantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }

    @Test
    void testDeleteConsultant() throws Exception {
        when(consultantService.deleteConsultant(Mockito.<String>any())).thenReturn(new ResponsePayload());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/v1/consultant/delete-consultant")
                .param("id", "foo");
        MockMvcBuilders.standaloneSetup(consultantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }

    @Test
    void testGetAvailabilityByDate() throws Exception {
        when(consultantService.getAvailabilityByDate(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new ResponsePayload());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/consultant/get-availability-by-date")
                .param("date", "foo")
                .param("id", "foo");
        MockMvcBuilders.standaloneSetup(consultantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }

    @Test
    void testGetConsultantDetails() throws Exception {
        when(consultantService.getConsultantDetails(Mockito.<String>any())).thenReturn(new ResponsePayload());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/consultant/get-consultant")
                .param("id", "foo");
        MockMvcBuilders.standaloneSetup(consultantController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"status\":null,\"message\":null,\"data\":null}"));
    }

    @Test
    void testRegisterConsultant() throws Exception {
        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setCountry("GB");
        consultantDTO
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantDTO.setEmail("jane.doe@example.org");
        consultantDTO.setFirstName("Jane");
        consultantDTO.setId("42");
        consultantDTO.setJobType("Job Type");
        consultantDTO.setLastName("Doe");
        consultantDTO.setMobile("Mobile");
        consultantDTO.setNic("Nic");
        consultantDTO.setPassword("iloveyou");
        consultantDTO.setRole(Role.ADMIN);
        consultantDTO.setStatus(true);
        consultantDTO.setTimeSlots(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(consultantDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/consultant/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(consultantController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void testUpdateConsultant() throws Exception {
        ConsultantDTO consultantDTO = new ConsultantDTO();
        consultantDTO.setCountry("GB");
        consultantDTO
                .setCreatedDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        consultantDTO.setEmail("jane.doe@example.org");
        consultantDTO.setFirstName("Jane");
        consultantDTO.setId("42");
        consultantDTO.setJobType("Job Type");
        consultantDTO.setLastName("Doe");
        consultantDTO.setMobile("Mobile");
        consultantDTO.setNic("Nic");
        consultantDTO.setPassword("iloveyou");
        consultantDTO.setRole(Role.ADMIN);
        consultantDTO.setStatus(true);
        consultantDTO.setTimeSlots(new ArrayList<>());
        String content = (new ObjectMapper()).writeValueAsString(consultantDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/consultant/update-consultant")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(consultantController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

}

