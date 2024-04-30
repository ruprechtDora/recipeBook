package com.econsult.recept.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.econsult.recept.model.entity.Users;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long> {
	Users findByUsernameAndPassword(String username, String password);
	
	Boolean existsByUsername(String username);
}
