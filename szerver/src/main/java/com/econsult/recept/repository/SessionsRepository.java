package com.econsult.recept.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.econsult.recept.model.entity.Sessions;

@Repository
public interface SessionsRepository extends CrudRepository<Sessions, String> {

}
