package io.post.novel.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.post.novel.auth.UserForm;
import io.post.novel.dto.PassValid;
import io.post.novel.dto.UserEdit;
import io.post.novel.dto.UserRequest;
import io.post.novel.entity.SignUpUser;
import io.post.novel.service.UserDisplayService;

@Controller
public class UserDisplayController {
	
	@Autowired UserDisplayService userDisplayService;
	
	@Autowired PasswordEncoder encoder;


	/*
	 * ログイン後のユーザーメニューページ表示
	 */
	@RequestMapping("/user/user_page")
	public String toUserPage(Model model, Principal principal) {
		
		//ログイン情報のセッションからユーザーネームを取得
		String loginUserName = principal.getName();
		
		//画面に渡すために取得した値をmodelに詰める
		model.addAttribute("login_user",loginUserName);
		
		return "user/user_page";
	}
	
	
	/*
	 *ユーザー詳細ページ表示 
	 */
	@RequestMapping("/user/user_info/{penName}")//ユーザーネームをpathに追加
	public String toUserInfo(@PathVariable("penName") String penName,Model model) {
		
		//pathからリクエストを取得してuserInfoSearchメソッドの引数に渡す
		SignUpUser signUpUser = userDisplayService.userInfoSearch(penName);
		
		//取得したオブジェクトをmodelに詰めて画面に表示
		model.addAttribute("user_info",signUpUser);
		
		//System.out.println(signUpUser); 出力チェック用
		
		return "user/user_info";
	}
	
	
	/*
	 * ユーザー情報編集ページ
	 */
	@PostMapping("/user/user_info/edit/{id}")//ユーザーidをpathに追加
	public String toUserEdit(@PathVariable("id") String id, @ModelAttribute UserRequest userEdit, Model model) {
		
		//pathから受け取ったidを引数にして編集するレコードの取得（あらかじめフォーム内に入れておくため）
		SignUpUser editUser = userDisplayService.userEditSearch(id);
		model.addAttribute("edit_user",editUser);
		
		//System.out.println(editUser);　レコードとれてるかの出力チェック用
		return "user/user_info_edit";
		
	}
	
	
	/*
	 * ユーザー情報変更
	 */
	@PostMapping("/user/user_info/edit/update")
	public String userUpdate(@ModelAttribute @Validated UserEdit userEdit, BindingResult result,  Model model,@AuthenticationPrincipal UserForm userForm) {
		
		//受けとったリクエストの入力チェック
		 if (result.hasErrors()) {
			 	model.addAttribute("edit_user", userEdit);
			  
			  return "user/user_info_edit";//エラー時は自画面遷移
	        }
		
		 //受け取ったオブジェクトを引数にDBのアップデート処理を実行
		userDisplayService.updateUserInfo(userEdit);
		
		//principalに更新したオブジェクトのユーザーネームをセットする
		userForm.setPenName(userEdit.getPenName());
		
		//setAuthenticationメソッドでUsernamePasswordAuthnticaionTolkenの中身の認証したセッション情報を更新する
		 SecurityContext auth = SecurityContextHolder.getContext();
		 auth.setAuthentication(new UsernamePasswordAuthenticationToken(userForm,userForm.getPenName(), userForm.getAuthorities()));
		
		 //ユーザーページへリダイレクト
		return "redirect:/user/user_page";
	}
	
	
	/*
	 * ユーザーパースワード変更フォームへ移動
	 */
	@GetMapping("/user/password/edit")
	public String toPassEdit(Model model) {
		model.addAttribute("pass_valid", new PassValid());
		
		return "user/edit_password";
	}
	
	
	/*
	 * ユーザーパスワードの入力チェック
	 */
	@PostMapping("/user/password/edit/check")
	public String passwordCheck(@ModelAttribute @Validated PassValid passValid, BindingResult result, Model model, @AuthenticationPrincipal UserForm userForm) {
		
		model.addAttribute("pass_valid", passValid);
		
		if (result.hasErrors()) {
			 	
			  
			  return "user/edit_password";//エラー時は自画面遷移
	        }
		
		//入力されたパスワードとセッションのパスワードの一致確認
		boolean passCheck = encoder.matches(passValid.getPassword(), userForm.getPassword());
		
		if(passCheck == true) {
			
			return "user/input_new_password";
		}
		
		return "user/edit_password";//一致しない場合は自画面繊維
	}
	
	
	/*
	 * パスワード変更処理実行
	 */
	@PostMapping("/user/change/password")
	public String passChange(@ModelAttribute @Validated PassValid passValid, BindingResult result, Model model, @AuthenticationPrincipal UserForm userForm) {
		
		model.addAttribute("change_pass", passValid);
		
		//レコード指定用のidをprincipalから取得
		//取得したidをpassValidにセット
		long id = userForm.getId();
		passValid.setId(id);
		
		if (result.hasErrors()) {
		 	
		  return "user/input_new_password";//エラー時は自画面遷移
        }
		
		//入力で受け取ったパスワードのハッシュ化
		String rawPassword = passValid.getPassword();
		passValid.setPassword(encoder.encode(rawPassword));
		
		//DBのデータをUpdate処理
		userDisplayService.updatePass(passValid);
		
		//ハッシュ化したパスワードをPrincipalにセット
		userForm.setPassword(passValid.getPassword());
		
		//認証情報の「ゆーざーねーむぱすわーどおうせんてぃけーしょんとーくん」を更新
		 SecurityContext auth = SecurityContextHolder.getContext();
		 auth.setAuthentication(new UsernamePasswordAuthenticationToken(userForm, userForm.getPassword(), userForm.getAuthorities()));
		
		return "redirect:/user/user_page";
		
	}

	
	/*
	 * デリート画面へ遷移
	 */
	@GetMapping("/user/user_delete")
	public String toDeleteDisplay() {
		
		return "user/delete";
	}
	
	
	/*
	 * デリートユーザー取得
	 */
	@PostMapping("/user/user_delete/check")
	public String deleteInfoCheck(@ModelAttribute UserRequest deleteUser, Model model,@AuthenticationPrincipal UserForm userForm) {
		
		
		//入力されたパスワードとセッションのパスワードの一致確認
		boolean passCheck = encoder.matches(deleteUser.getPassword(), userForm.getPassword());
		
		//入力で受け取ったパスワードのハッシュ化
		String rawPassword = deleteUser.getPassword();
		deleteUser.setPassword(encoder.encode(rawPassword));
		
		//入力内容と一致しない場合は自画面遷移
		if(passCheck != true) {
					
			return "user/delete";
		}
		
		//セッションからidを取得してdeleteUserにセット
		deleteUser.setId(userForm.getId());
		
		//削除対象のユーザー情報をDBから取得
		SignUpUser deleteUserInfo = userDisplayService.deleteUserSelect(deleteUser);
		//取得内容をmodelに詰めて画面へ表示
		model.addAttribute("delete_user", deleteUserInfo);
		
		return "user/delete_check";
	}
	
	/*
	 * デリート処理実行
	 */
	@PostMapping("/user/user_delete/check/{id}")
	public String delete(@ModelAttribute UserRequest delete, @PathVariable("id") String id) {
		//pathでわたってきたidを使ってユーザーデリート実行
		userDisplayService.delete(id);
		
		//リダイレクトでログアウト
		return "redirect:/signout";
	}
 }
