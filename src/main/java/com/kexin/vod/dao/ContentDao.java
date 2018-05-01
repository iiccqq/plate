package com.kexin.vod.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kexin.vod.model.Content;

@Repository
public class ContentDao {

	@Resource
	JdbcTemplate jdbcTemplate;

	public List<Content> getContentsByCatalogId(Integer catalogId) {
		String sql = "select * from vod_content where catalog_id = ?";
		List<Content> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Content>(Content.class), catalogId);
		return list;
	}

	public Content getContentById(Integer contentId) {
		String sql = "select * from vod_content where content_id = ?";
		List<Content> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Content>(Content.class), contentId);
		if (list.size() > 0)
			return list.get(0);
		else
			return new Content();
	}

}
