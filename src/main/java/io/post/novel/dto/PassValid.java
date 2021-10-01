package io.post.novel.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class PassValid {

	 	private long id;
		private String penName;
		
		@NotBlank(message = "パスワードは必須項目です")
		@Size(max = 20, min = 8 , message = "8桁以上20桁以内で設定してください")
		@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "半角英数字で設定してください")
		private String password;
		
		@NotBlank(message = "パスワードは必須項目です")
		@Size(max = 20, min = 8 , message = "8桁以上20桁以内で設定してください")
		@Pattern(regexp = "^[a-zA-Z0-9]+$", message = "半角英数字で設定してください")
		private String passwordConfirm;
	
		@AssertTrue
		public boolean isPasswordConfirmed() {	//@AssertTrueの場合メソッド名はisで始まる（boolean型のゲッターメソッドのルールに従う）
			if (password == null && passwordConfirm == null) {
				return true;
			}
			return password.equals(passwordConfirm);
		}
}
