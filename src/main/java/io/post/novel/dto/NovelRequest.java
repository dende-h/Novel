package io.post.novel.dto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
public class NovelRequest {

	private int novelId;
	private int genre;
	private int novelLength;
	private String title;
	private int chapter;
	private int section;
	private int clause;
	private String text;
	@ManyToOne(targetEntity = UserRequest.class)
	@JoinColumn(name = "user_id" )
	private int userId;
	
	
	
	
}
