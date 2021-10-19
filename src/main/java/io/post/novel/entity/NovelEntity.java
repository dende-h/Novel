package io.post.novel.entity;

import lombok.Data;

@Data
public class NovelEntity {

	private int id;
	private int genre;
	private int novelLength;
	private String title;
	private String text;
	private long userId;
	
	
}
