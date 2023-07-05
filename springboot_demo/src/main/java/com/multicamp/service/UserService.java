package com.multicamp.service;

import java.util.List;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.UserVO;

public interface UserService {

	int createUser(UserVO user);

	int getUserCount(PagingVO pvo);

	List<UserVO> listUser(PagingVO pvo);

	boolean idCheck(String userid);

	int deleteUser(Integer midx);

	int updateUser(UserVO user);

	UserVO getUser(Integer midx);
	
	UserVO findUser(UserVO user);
}
