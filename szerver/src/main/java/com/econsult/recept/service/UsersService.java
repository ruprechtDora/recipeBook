package com.econsult.recept.service;

import java.util.Base64;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.econsult.recept.model.dto.SaveUserDto;
import com.econsult.recept.model.dto.UpdateUserDto;
import com.econsult.recept.model.entity.Sessions;
import com.econsult.recept.model.entity.Users;
import com.econsult.recept.repository.UsersRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UsersService {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private UsersRepository usersRepository;
	
	public ResponseEntity<Void> saveUser(SaveUserDto saveUserDto) {
		try {
			if(saveUserDto.getUsername() == null || saveUserDto.getUsername().isBlank())
				throw new NullPointerException("Username is empty!");
			
			if(saveUserDto.getPassword() == null || saveUserDto.getPassword().isBlank())
				throw new NullPointerException("Password is empty!");
			
			// Ha már a user létezett, akkor nem értesítjük hogy már létezik biztonsági okokból.
			if(userExists(saveUserDto.getUsername()))
				return ResponseEntity.ok().build();
			
			Users newUser = new Users();
			newUser.setUsername(saveUserDto.getUsername());
			
			String md5Password = DigestUtils.md5Hex(saveUserDto.getPassword()).toUpperCase();
			
			newUser.setPassword(md5Password);
			newUser.setFirstName(saveUserDto.getFirstName());
			newUser.setLastName(saveUserDto.getLastName());
			
			usersRepository.save(newUser);
					
			return ResponseEntity.ok().build();
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	public ResponseEntity<Users> updateUser(UpdateUserDto updateUserDto) {
		try {
			if(updateUserDto.getId() == null)
				throw new NullPointerException("ID is missing!");
			
			Users user = usersRepository.findById(updateUserDto.getId()).get();
			
			user.setFirstName(updateUserDto.getFirstName());
			user.setLastName(updateUserDto.getLastName());
			
			Users savedUser = usersRepository.save(user);
							
			return ResponseEntity.ok(savedUser);
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private Boolean userExists(String username) {
		return usersRepository.existsByUsername(username);
	}
	
	public ResponseEntity<Users> getUserById(Long id) {
		try {
			return ResponseEntity.ok(usersRepository.findById(id).get());
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	public ResponseEntity<Users> getUserBySessionId(String sessionId) {
		try {
			Sessions session = authenticationService.getSessionBySessionId(sessionId);
			return ResponseEntity.ok(usersRepository.findById(session.getUserId()).get());
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	public ResponseEntity<Void> uploadProfileImage(Long id, MultipartFile file) {
		try {
			Users user = usersRepository.findById(id).get();
			
			byte[] image = file.getBytes();
			
			String imageEncoded = Base64.getEncoder().encodeToString(image);
			
			user.setImage(imageEncoded);
			
			usersRepository.save(user);
			
			return ResponseEntity.ok().build();
		} catch(Exception e) {
			log.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
