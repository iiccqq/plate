package com.kexin.vod.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kexin.vod.dao.ContentDao;
import com.kexin.vod.model.Content;

@RestController
public class ContentController {

	@Resource
	ContentDao contentDao;

	@RequestMapping("/contents")
	List<Content> getContentByCatalogId(@RequestParam Integer catalogId) {
		return contentDao.getContentsByCatalogId(catalogId);
	}
	@RequestMapping("/content/{contentId}")
	Content getContentById(@PathVariable Integer contentId) {
		return contentDao.getContentById(contentId);
	}
	
}
