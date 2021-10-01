package io.post.novel.entity;

import lombok.Data;

@Data
public class SignUpUser {

	private long id;
	private String penName;
	private String eMail;
	private String password;
	private long birthYear;
	private long birthMonth;
	private long birthDay;
	private String userCategory;
	
}
