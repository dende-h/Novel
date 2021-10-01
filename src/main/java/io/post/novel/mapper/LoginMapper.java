package io.post.novel.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.post.novel.auth.UserForm;

@Mapper
public interface LoginMapper {

	public UserForm identifyUser(String username);

}
