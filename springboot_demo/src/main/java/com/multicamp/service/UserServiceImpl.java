package com.multicamp.service;

import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.UserVO;
import com.multicamp.mapper.UserMapper;

@Service("userService")
public class UserServiceImpl  implements UserService {
	
	@Resource(name="bCryptPasswordEncoder")
	private PasswordEncoder passwordEncoder;
	
	@Inject
	private UserMapper userMapper;

	@Override
	public int createUser(UserVO user) {
		String encodePasswd=passwordEncoder.encode(user.getPasswd());
		user.setPasswd(encodePasswd);
		
		return userMapper.createUser(user);
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

	

}
