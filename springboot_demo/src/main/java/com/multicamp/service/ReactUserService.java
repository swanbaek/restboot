package com.multicamp.service;

import java.util.List;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.ReactUserVO;

public interface ReactUserService {

	int createUser(ReactUserVO user);

	int getUserCount(PagingVO pvo);

	List<ReactUserVO> listUser(PagingVO pvo);

	boolean nickCheck(String nickname);

	int deleteUser(Integer midx);

	int updateUser(ReactUserVO user);

	ReactUserVO getUser(Integer midx);
	
	ReactUserVO findUser(ReactUserVO user);
}
