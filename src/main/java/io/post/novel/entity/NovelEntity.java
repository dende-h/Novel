package io.post.novel.entity;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
public class NovelEntity {

	private int novelId;
	private int genre;
	private int novelLength;
	private String title;
	private int chapter;
	private int section;
	private int clause;
	private String text;
	@ManyToOne(targetEntity = SignUpUser.class)
	@JoinColumn(name = "user_id")
	private int userId;
	
	
}
