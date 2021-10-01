package io.post.novel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.post.novel.dto.NovelRequest;
import io.post.novel.mapper.NovelMapper;

@Service
public class NovelService {
	
	@Autowired NovelMapper novelMapper;

	public void save(NovelRequest draft) {
		
		novelMapper.save(draft);
		
	}

}
