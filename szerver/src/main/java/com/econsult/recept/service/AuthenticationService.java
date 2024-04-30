package com.econsult.recept.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.econsult.recept.model.dto.CheckSessionResponse;
import com.econsult.recept.model.dto.LoginDto;
import com.econsult.recept.model.entity.Sessions;
import com.econsult.recept.model.entity.Users;
import com.econsult.recept.repository.SessionsRepository;
import com.econsult.recept.repository.UsersRepository;

import javax.persistence.NoResultException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthenticationService {

	@Autowired
	private SessionsRepository authenticationRepository;
	
	@Autowired
	private UsersRepository usersRepository;
	
	public ResponseEntity<Sessions> login(LoginDto loginDto) {
		try {
			String sessionId = UUID.randomUUID().toString();
			String md5Password = DigestUtils.md5Hex(loginDto.getPassword()).toUpperCase();
			Sessions newSession = new Sessions(sessionId, getUserIdFromCredentials(loginDto.getUsername(), md5Password).getId(), LocalDateTime.now(), LocalDateTime.now().plusHours(8));
			Sessions savedSession = authenticationRepository.save(newSession);
			
			return ResponseEntity.ok(savedSession);
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private Users getUserIdFromCredentials(String username, String password) throws NoResultException {
		Users user = usersRepository.findByUsernameAndPassword(username, password);
		
		if(user == null) {
			throw new NoResultException("User not found, or password is incorrect!");
		}
		
		return user;
	}
	
	public Sessions getSessionBySessionId(String sessionId) {
		try {
			return authenticationRepository.findById(sessionId).get();
		} catch(Exception e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public ResponseEntity<CheckSessionResponse> checkSession(String sessionId) {
		Sessions session = getSessionBySessionId(sessionId);
		
		if(session != null) {
			if(session.getExpirationTimestamp().isAfter(LocalDateTime.now()))
				return ResponseEntity.ok(new CheckSessionResponse(true));
		}
		return ResponseEntity.ok(new CheckSessionResponse(false));
	}
}
