package com.multicamp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.UserVO;
@Service("reactUserService")
public class ReactUserServiceImpl implements UserService {

	@Override
	public int createUser(UserVO user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getUserCount(PagingVO pvo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<UserVO> listUser(PagingVO pvo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean idCheck(String userid) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteUser(Integer midx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUser(UserVO user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public UserVO getUser(Integer midx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserVO findUser(UserVO user) {
		// TODO Auto-generated method stub
		return null;
	}

}
