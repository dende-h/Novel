package io.post.novel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.post.novel.dto.NovelRequest;
import io.post.novel.entity.NovelEntity;
import io.post.novel.mapper.NovelMapper;

@Service
public class NovelService {
	
	@Autowired NovelMapper novelMapper;

	public void save(NovelRequest draft) {
		
		novelMapper.save(draft);
		
	}

	public List<NovelEntity> draftList(long userId) {

		return novelMapper.draftList(userId);
	}

}
