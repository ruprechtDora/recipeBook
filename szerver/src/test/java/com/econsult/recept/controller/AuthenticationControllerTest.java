package com.econsult.recept.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.econsult.recept.model.dto.CheckSessionResponse;
import com.econsult.recept.model.dto.LoginDto;
import com.econsult.recept.model.entity.Sessions;
import com.econsult.recept.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {
	
	@Autowired
	private MockMvc mvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testLogin() throws Exception {
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername("testUser");
        loginDto.setPassword("testPassword");

        Sessions session = new Sessions();
        session.setSessionId("sessionId");
        session.setUserId(1L);
        session.setLoginTimestamp(LocalDateTime.now());
        session.setExpirationTimestamp(LocalDateTime.now().plusHours(8));
        
        when(authenticationService.login(Mockito.any(LoginDto.class)))
               .thenReturn(new ResponseEntity<>(session, HttpStatus.OK));

        mvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.sessionId").value("sessionId"));
    }
    
    @Test
    public void testCheckSession() throws Exception {
    	String sessionId = "sessionId";
    	CheckSessionResponse response = new CheckSessionResponse(true);
    	when(authenticationService.checkSession(Mockito.any(String.class)))
        .thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
    	
    	mvc.perform(get("/auth/checksession/{sessionId}", sessionId))
        .andExpect(status().isOk());
    }

}