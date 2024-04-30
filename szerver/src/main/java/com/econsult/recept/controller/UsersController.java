package com.econsult.recept.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.econsult.recept.model.dto.SaveUserDto;
import com.econsult.recept.model.dto.UpdateUserDto;
import com.econsult.recept.model.entity.Users;
import com.econsult.recept.service.UsersService;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	private UsersService usersService;
	
	@PostMapping(path = "/")
	public ResponseEntity<Void> saveUser(@RequestBody SaveUserDto saveUserDto) {
		return usersService.saveUser(saveUserDto);
	}
	
	@GetMapping(path = "/{id}")
	public ResponseEntity<Users> getUserById(@PathVariable("id") Long id) {
		return usersService.getUserById(id);
	}
	
	@GetMapping(path = "/bysession/{sessionId}")
	public ResponseEntity<Users> getUserBySessionId(@PathVariable("sessionId") String sessionId) {
		return usersService.getUserBySessionId(sessionId);
	}
	
	@PutMapping(path = "/", consumes = "application/json")
	public ResponseEntity<Users> updateUser(@RequestBody UpdateUserDto updateUserDto) {
		return usersService.updateUser(updateUserDto);
	}
	
	@PostMapping(path = "/{id}/uploadprofileimage")
	public ResponseEntity<Void> uploadProfileImage(@PathVariable("id") Long id, @RequestParam("profil-image") MultipartFile file) {
		return usersService.uploadProfileImage(id, file);
	}
}
