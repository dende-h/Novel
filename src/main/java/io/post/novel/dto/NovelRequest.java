package io.post.novel.dto;

import lombok.Data;

@Data
public class NovelRequest {

	private int genre;
	private int novelLength;
	private String title;
	private int chapter;
	private int section;
	private int clause;
	private String text;
	private long userId;
	
	
	
	
}
