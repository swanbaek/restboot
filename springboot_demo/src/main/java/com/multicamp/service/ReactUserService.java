package com.multicamp.service;

import java.util.List;

import com.multicamp.domain.PagingVO;
import com.multicamp.dto.ReactUserDTO;

public interface ReactUserService {

	int createUser(ReactUserDTO user);

	int getUserCount(PagingVO pvo);

	List<ReactUserDTO> listUser(PagingVO pvo);

	boolean nickCheck(String nickname);

	int deleteUser(Integer midx);

	int updateUser(ReactUserDTO user);

	ReactUserDTO getUser(Integer midx);
	
	ReactUserDTO findUser(ReactUserDTO user);
}
