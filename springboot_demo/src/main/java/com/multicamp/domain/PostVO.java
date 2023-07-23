package com.multicamp.domain;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class PostVO {
	
	private int id;
	private String name;
	private String content;
	private String filename;
	private Date wdate;
	private MultipartFile mfilename;

}
