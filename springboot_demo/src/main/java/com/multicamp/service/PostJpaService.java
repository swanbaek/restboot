package com.multicamp.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.multicamp.domain.PagingVO;
import com.multicamp.domain.PostEntity;
import com.multicamp.persistence.PostRepository;

@Service("postJpaService")
public class PostJpaService{
	
	Logger log=LoggerFactory.getLogger(getClass());
	
	@Inject
	private PostRepository repository;
	public List<PostEntity> create(final PostEntity entity){
		if(entity==null) {
			throw new RuntimeException("PostEntity cannot be null");
		}
		if(entity.getName()==null) {
			throw new RuntimeException("writer's name is null");
		}
		///////////////////////
		repository.save(entity);//insert문을 실행함
		/////////////////////////
		log.info("Entity Id: {} is saved", entity.getId());
		//return this.repository.findByName(entity.getName());
		return this.repository.findAll();
	}
	
	
	public int getPostCount(PagingVO page) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public List<PostEntity> listPosts(PagingVO page) {
		// TODO Auto-generated method stub
		return this.repository.findAll();
	}
	public int updatePost(final PostEntity vo) {
		// TODO Auto-generated method stub
		PostEntity post=this.repository.findById(vo.getId()).orElseThrow();
		post.setContent(vo.getContent());
		post.setFilename(vo.getFilename());
		post.setName(vo.getName());
		post.setWdate(vo.getWdate());
		this.repository.save(post);
		return 1;
		
	}
	public int deletePost(int id) {
		this.repository.deleteById(id);
		return 1;
	}

}
