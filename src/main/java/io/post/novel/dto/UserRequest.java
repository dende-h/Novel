package io.post.novel.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserRequest {
	
	private long id;
	
	@NotBlank(message = "ペンネームは必須項目です")
	@Size(max = 25, message = "ペンネームは25文字以内で設定してください")
	private String penName;
	
	@NotBlank(message = "メールアドレスは必須項目です")
	//@Pattern(regexp = "^(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)$",message = "メールアドレスを入力してください")
	@Email(message = "emailの形式で入力してください")
	private String eMail;
	
	
	@NotBlank(message = "パスワードは必須項目です")
	@Size(max = 20, min = 8 , message = "8桁以上20桁以内で設定してください")
	@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "半角英数字で設定してください")
	private String password;
	
	
	@Positive(message = "選択必須項目です")
	private long birthYear;
	
	
	@Positive(message = "選択必須項目です")
	private long birthMonth;
	
	
	@Positive(message = "選択必須項目です")
	private long birthDay;
	
	
	@NotBlank(message = "選択必須項目です")
	private String userCategory;
	
	
}
