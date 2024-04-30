package com.econsult.recept.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.econsult.recept.model.dto.CheckSessionResponse;
import com.econsult.recept.model.dto.LoginDto;
import com.econsult.recept.model.entity.Sessions;
import com.econsult.recept.service.AuthenticationService;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping(path = "login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<Sessions> login(@RequestBody LoginDto loginDto) {
		return authenticationService.login(loginDto);
	}
	
	@GetMapping(path = "/checksession/{sessionId}")
	public ResponseEntity<CheckSessionResponse> checkSession(@PathVariable("sessionId") String sessionId) {
		return authenticationService.checkSession(sessionId);
	}
}
