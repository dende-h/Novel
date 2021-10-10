package io.post.novel.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import io.post.novel.dto.NovelRequest;


@Mapper
public interface NovelMapper {
	
	@Insert("INSERT INTO draft_novels(genre,novel_length,title,chapter,section,clause,text,user_id)"
			+ "VALUES (#{genre},#{novelLength},#{title},#{chapter},#{section},#{clause},#{text},#{userId})")
	void save(NovelRequest draft);

}
