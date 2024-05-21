package com.multicamp.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
//react와 jpa연동시 사용할 vo클래스
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactUserDTO {
	private String token;//token추가
	private String refreshToken;//refreshToken추가
	
	private Long idx;//int=>Long으로 변경
	private String name;
	private String nickname;
	private String pwd;
	private String role;
	private Date indate;

}
