package com.multicamp.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.ReactUserVO;
import com.multicamp.mapper.ReactUserMapper;

import lombok.RequiredArgsConstructor;
@Service("reactUserService")
@RequiredArgsConstructor
public class ReactUserServiceImpl implements ReactUserService {

	private final ReactUserMapper userMapper;
	

	
	@Inject
	private BCryptPasswordEncoder passwordEncoder;
	@Override
	public int createUser(ReactUserVO user) {
		user.setPwd(passwordEncoder.encode(user.getPwd()));
		return userMapper.createUser(user);
	}

	@Override
	public int getUserCount(PagingVO pvo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ReactUserVO> listUser(PagingVO pvo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean nickCheck(String nickname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int deleteUser(Integer midx) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateUser(ReactUserVO user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ReactUserVO getUser(Integer midx) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ReactUserVO findUser(ReactUserVO user) {
		// TODO Auto-generated method stub
		return null;
	}

}
