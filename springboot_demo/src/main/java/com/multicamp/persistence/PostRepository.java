package com.multicamp.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.multicamp.domain.PostEntity;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer> {
	
	//메서드 이름이 query문, 매개변수는 query의 where절에 들어갈 값을 의미
	//쿼리문이 복잡할 때는 @Query(쿼리문)어노테이션을 붙이고 상세 쿼리를 기술함
   List<PostEntity> findByName(String name);
   
   List<PostEntity> findAll();

}
