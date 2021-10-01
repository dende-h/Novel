package io.post.novel.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserEdit {
	
	private long id;
	
	@NotBlank(message = "ペンネームは必須項目です")
	@Size(max = 25, message = "ペンネームは25文字以内で設定してください")
	private String penName;
	
	@NotBlank(message = "メールアドレスは必須項目です")
	//@Pattern(regexp = "^(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)$",message = "メールアドレスを入力してください")
	@Email(message = "emailの形式で入力してください")
	private String eMail;
	
	
	private long birthYear;
	
	
	private long birthMonth;
	
	
	private long birthDay;
	
	
	@NotBlank(message = "選択必須項目です")
	private String userCategory;
	
	
}
