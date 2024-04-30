package com.econsult.recept.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SESSIONS")
public class Sessions {

	@Id
	private String sessionId;
	private Long userId;
	private LocalDateTime loginTimestamp;
	private LocalDateTime expirationTimestamp;
	
}
