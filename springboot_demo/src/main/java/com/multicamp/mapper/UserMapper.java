package com.multicamp.mapper;

import com.multicamp.domain.UserVO;

public interface UserMapper {
	
	int createUser(UserVO user);
	UserVO getUser(int idx);
	
	Integer idCheck(String userid);
	UserVO findUser(UserVO user);
	
	int deleteUser(int idx);
	int updateUser(UserVO user);

}
