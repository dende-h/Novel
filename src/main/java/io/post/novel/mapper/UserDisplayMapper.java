package io.post.novel.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.post.novel.dto.PassValid;
import io.post.novel.dto.UserEdit;
import io.post.novel.dto.UserRequest;
import io.post.novel.entity.SignUpUser;

@Mapper
public interface UserDisplayMapper {
	
	@Select("SELECT * FROM users WHERE pen_name = #{penName}")
	SignUpUser selectName(UserRequest userName);
	
	
	@Select("SELECT * FROM users WHERE pen_name = #{penName}")
	SignUpUser userInfoSearch(String penName);
	
	@Select("SELECT * FROM users WHERE id = #{id}")
	SignUpUser userEditSearch(String id);
	
	@Update("UPDATE users "
			+ "SET pen_name = #{penName}, e_mail = #{eMail}, user_category = #{userCategory} "
			+ "WHERE id = #{id}")
	void updateUserInfo(UserEdit userEdit);

	@Select("SELECT * FROM users WHERE pen_name = #{penName} AND id = #{id}")
	SignUpUser deleteUserSelect(UserRequest deleteUser);

	@Delete("DELETE FROM users WHERE id = #{id}")
	void delete(String id);

	@Update("UPDATE users "
			+ "SET password = #{password}"
			+ "WHERE id = #{id}")
	void updatePass(PassValid passValid);

	//@Select("SELECT COUNT(*) FROM users WHERE id = #{id} AND password = #{password}")
	//int findUserOne(PassValid passValid);

	
}
