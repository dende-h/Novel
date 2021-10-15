package io.post.novel.error.aspect;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpSessionRequiredException;

@Aspect
@Component
public class ErrorAspect {

	@AfterThrowing(value = "execution(**..*..*(..))" 
			+ "&&(bean(*Controller)||bean(*Service)||bean(*Repositoy)"
			,throwing = "ex")
	public void throwingNull(HttpSessionRequiredException ex)
	{
		System.out.println("エラーが発生しました");
	}
	
	
}
