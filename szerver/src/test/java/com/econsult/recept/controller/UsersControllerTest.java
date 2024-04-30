package com.econsult.recept.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.econsult.recept.model.dto.SaveUserDto;
import com.econsult.recept.model.dto.UpdateUserDto;
import com.econsult.recept.model.entity.Users;
import com.econsult.recept.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UsersController.class)
@AutoConfigureMockMvc(addFilters = false)
public class UsersControllerTest {

	@Autowired
	private MockMvc mvc;

    @MockBean
    private UsersService usersService;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testSaveUser() throws Exception {
        SaveUserDto saveUserDto = new SaveUserDto();
        saveUserDto.setUsername("testUser");
        saveUserDto.setPassword("testPassword");
        saveUserDto.setFirstName("John");
        saveUserDto.setLastName("Doe");

        when(usersService.saveUser(Mockito.any(SaveUserDto.class)))
               .thenReturn(new ResponseEntity<>(HttpStatus.OK));

        mvc.perform(post("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(saveUserDto)))
        .andExpect(status().isOk());
    }
    
    @Test
    public void testGetUserById() throws Exception {
        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");

        when(usersService.getUserById(userId))
               .thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        mvc.perform(get("/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }
    
    @Test
    public void testGetUserBySessionId() throws Exception {
        String sessionId = "testSessionId";
        Long userId = 1L;
        Users user = new Users();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Doe");

        when(usersService.getUserBySessionId(sessionId))
               .thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        mvc.perform(get("/users/bysession/{sessionId}", sessionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }
    
    @Test
    public void testUpdateUser() throws Exception {
        UpdateUserDto updateUserDto = new UpdateUserDto();
        updateUserDto.setId(1L);
        updateUserDto.setFirstName("UpdatedFirstName");
        updateUserDto.setLastName("UpdatedLastName");

        Users updatedUser = new Users();
        updatedUser.setId(updateUserDto.getId());
        updatedUser.setFirstName(updateUserDto.getFirstName());
        updatedUser.setLastName(updateUserDto.getLastName());

        when(usersService.updateUser(Mockito.any(UpdateUserDto.class)))
               .thenReturn(new ResponseEntity<>(updatedUser, HttpStatus.OK));

        mvc.perform(put("/users/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateUserDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(updateUserDto.getId()))
        .andExpect(jsonPath("$.firstName").value("UpdatedFirstName"))
        .andExpect(jsonPath("$.lastName").value("UpdatedLastName"));
    }
    
}
