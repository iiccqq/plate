package com.kexin.vod.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.kexin.vod.model.Content;

@Repository
public class CarouselDao {

	@Resource 
	JdbcTemplate jdbcTemplate;
	public List<Content> getContent() {
		String sql = "select c.*,car.image from vod_content c ,vod_carousel car where c.content_id = car.content_id";
		List<Content> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<Content>(Content.class));
		return list;
	}

}
