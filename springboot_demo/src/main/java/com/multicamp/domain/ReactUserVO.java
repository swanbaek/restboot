package com.multicamp.domain;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReactUserVO {
	
	private int idx;
	private String name;
	private String nickname;
	private String pwd;
	private String role;
	private Date indate;

}
