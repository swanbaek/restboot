package com.multicamp.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="REACT_POST")
@SequenceGenerator(name="REACT_POST_SEQ_GEN",//시퀀스 제너레이터 이름
sequenceName = "REACT_POST_SEQ", //시퀀스명
initialValue = 1, //시작값
allocationSize = 1)//메모리 통해 할당할 범위 사이즈
public class PostEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REACT_POST_SEQ_GEN")
	private int id;
	private String name;
	private String content;
	private String filename;
	private Date wdate;

}
