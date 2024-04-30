package com.econsult.recept.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

	private Long id;
	private String password;
	private String firstName;
	private String lastName;
	
}
