package com.multicamp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.PostVO;
import com.multicamp.mapper.PostMapper;

import lombok.RequiredArgsConstructor;

@Service("postService")
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
	
	private final PostMapper postMapper;

	@Override
	public int createPost(PostVO vo) {
		return postMapper.createPost(vo);
	}

	@Override
	public int getPostCount(PagingVO page) {
		// TODO Auto-generated method stub
		return postMapper.getPostCount(page);
	}

	@Override
	public List<PostVO> listPosts(PagingVO page) {
		// TODO Auto-generated method stub
		return postMapper.listPosts(page);
	}

	@Override
	public int updatePost(PostVO vo) {
		// TODO Auto-generated method stub
		return postMapper.updatePost(vo);
	}

	@Override
	public int deletePost(int id) {
		// TODO Auto-generated method stub
		return postMapper.deletePost(id);
	}

}
