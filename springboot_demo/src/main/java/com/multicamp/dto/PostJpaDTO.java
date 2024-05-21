package com.multicamp.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostJpaVO {
	
	private Long id;
	private String name;
	private String content;
	private String filename;
	private Date wdate;
	private MultipartFile mfilename;
	
	public PostJpaVO(final PostEntity entity) {
		this.id=entity.getId();
		this.name=entity.getName();
		this.content=entity.getContent();
		this.filename=entity.getFilename();
		this.wdate=entity.getWdate();
	}
	
	public static PostEntity toEntity(final PostJpaVO vo) {
		return PostEntity.builder()
				.id(vo.getId())
				.name(vo.name)
				.content(vo.getContent())
				.filename(vo.getFilename())
				.wdate(vo.getWdate())
				.build();
	}

}
