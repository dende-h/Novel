package io.post.novel.mapper;

import org.apache.ibatis.annotations.Mapper;

import io.post.novel.dto.UserRequest;

@Mapper
public interface UserSignUpMapper {

	int create(UserRequest userAdd);

	int createRole(UserRequest userAdd);
}
