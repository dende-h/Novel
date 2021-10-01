package io.post.novel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import io.post.novel.service.UserDetailsServiceImpl;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		/*
		 * DBに入れるパスワード生成のためのコード
		 *
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String password = "dende3641";
				String loginPass = bCryptPasswordEncoder.encode(password);
		System.out.println(loginPass);
		*/
		
		return new BCryptPasswordEncoder();
	}

	/*
	 * securityの対象外を設定
	 */
	
	@Override
	public void configure(WebSecurity web)throws Exception{
		//securityを適用しない
		
		web.ignoring()
			.antMatchers("/webjars/**")
			.antMatchers("/css/**")
			.antMatchers("/JS/**");
		
	}
	
	
	/*
	 * securityの各種設定
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
	
		//ログインページ不要設定
		http.authorizeRequests()
			.antMatchers("/top").permitAll() //直リンクOK
			.antMatchers("/sign_in").permitAll() //直リンクOK
			.antMatchers("/signup").permitAll() //直リンクOK
			.antMatchers("/input/check").permitAll() //直リンクOK
			.antMatchers("/signup/complete").permitAll() //直リンクOK
			.anyRequest().authenticated(); //それ以外は直リンクNG
	
		//ログイン処理	
		http.formLogin().loginProcessingUrl("/user/user_page") //ログイン処理のパス
						.loginPage("/sign_in")	//ログインページの指定
						.failureUrl("/sign_in?error")	//ログイン失敗時の遷移先
						.usernameParameter("penName")	//ログインページのユーザID
						.passwordParameter("password")	//ログインページのパスワード
						.defaultSuccessUrl("/user/user_page",true)	//成功後の遷移先
						.and()
						.logout().logoutUrl("/signout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID");
						
		
		
		//CSRF対策を無効に設定（一時的）
		
		http.csrf().disable();
		
	}
		
		/*
		 * 認証の設定
		 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth)throws Exception{
		
		PasswordEncoder encoder = passwordEncoder();
		
		//インメモリ認証
		/*
		auth
		.inMemoryAuthentication()
		.withUser("user")
		.password(encoder.encode("user"))
		.roles("user")
		.and()
		.withUser("admin")
		.password(encoder.encode("admin"))
		.roles("admin");
		*/
		
		//ユーザーデータで認証
		
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(encoder);
		
	}
	
}
