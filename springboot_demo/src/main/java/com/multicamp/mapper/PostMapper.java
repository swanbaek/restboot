package com.multicamp.mapper;

import java.util.List;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.PostVO;

public interface PostMapper {

	int createPost(PostVO vo);

	int getPostCount(PagingVO page);

	List<PostVO> listPosts(PagingVO page);

	int updatePost(PostVO vo);

	int deletePost(int id);

}
