package io.post.novel.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.Data;

@Data
public class NovelRequest {
	
	@Positive
	private int genre;
	
	@Positive
	private int novelLength;
	
	@NotBlank
	private String title;
	
	private int chapter;
	
	private int section;
	
	private int clause;
	
	private String text;
	
	private long userId;
	
	
	
	
}
