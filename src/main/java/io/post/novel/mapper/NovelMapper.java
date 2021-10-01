package io.post.novel.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import io.post.novel.dto.NovelRequest;
import io.post.novel.entity.NovelEntity;


@Mapper
public interface NovelMapper {
	
	@Insert("INSERT INTO draft_novels(genre,novel_length,title,chapter,section,clause,text,user_id)"
			+ "VALUES (#{genre},#{novelLength},#{title},#{chapter},#{section},#{clause},#{text},#{userId})")
	NovelEntity save(NovelRequest draft);

}
