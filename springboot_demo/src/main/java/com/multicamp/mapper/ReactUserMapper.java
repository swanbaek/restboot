package com.multicamp.mapper;

import com.multicamp.domain.ReactUserVO;

public interface ReactUserMapper {
	
	int createUser(ReactUserVO user);
	ReactUserVO getUser(int idx);
	
	Integer idCheck(String userid);
	ReactUserVO findUser(ReactUserVO user);
	
	int deleteUser(int idx);
	int updateUser(ReactUserVO user);

}
