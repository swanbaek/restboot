package com.multicamp.domain;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseVO<T> {
	
	private String error;
	private List<T> data;

}
