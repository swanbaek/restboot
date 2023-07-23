package com.multicamp.service;

import java.util.List;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.PostVO;

public interface PostService {
	
	int createPost(PostVO vo);
	
	int getPostCount(PagingVO page);
	List<PostVO> listPosts(PagingVO page);
	
	int updatePost(PostVO vo);
	int deletePost(int id);

}
