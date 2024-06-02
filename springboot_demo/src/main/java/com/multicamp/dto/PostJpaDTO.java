package com.multicamp.dto;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.multicamp.domain.PostEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostJpaDTO {
	
	private Long id;
	private String name;
	private String content;
	private String filename; 
	private Date wdate;
	private MultipartFile mfilename;
	
	public PostJpaDTO(final PostEntity entity) {
		this.id=entity.getId();
		this.name=entity.getName();
		this.content=entity.getContent();
		this.filename=entity.getFilename();
		this.wdate=entity.getWdate();
	}
	
	public static PostEntity toEntity(final PostJpaDTO vo) {
		return PostEntity.builder()
				.id(vo.getId())
				.name(vo.name)
				.content(vo.getContent())
				.filename(vo.getFilename())
				.wdate(vo.getWdate())
				.build();
	}

}
