package com.multicamp.mapper;

import com.multicamp.dto.ReactUserDTO;

public interface ReactUserMapper {
	
	int createUser(ReactUserDTO user);
	ReactUserDTO getUser(int idx);
	
	Integer idCheck(String userid);
	ReactUserDTO findUser(ReactUserDTO user);
	
	int deleteUser(int idx);
	int updateUser(ReactUserDTO user);

}
