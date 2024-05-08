package com.multicamp.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserVO  
{
	
	private int idx;
	private String name;
	private String userid;
	private String passwd;
	private String role;
	
	private String hp1;
	private String hp2;
	private String hp3;
	
	private String post;
	private String addr1;
	private String addr2;
	
	private java.sql.Date indate;
	
	public String getAllHp() {
		return hp1+"-"+hp2+"-"+hp3;
	}
	public String getAllAddr() {
		return "["+post+"] "+addr1+" "+addr2;
	}
	
	
}///////////////////////////////////
