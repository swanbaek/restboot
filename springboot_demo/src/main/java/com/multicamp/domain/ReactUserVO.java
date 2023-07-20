package com.multicamp.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class ReactUserVO {
	
	private int idx;
	private String name;
	private String nickname;
	private String pwd;
	private String role;
	private Date indate;

}
