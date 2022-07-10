package com.saitama.microservices.blogservice.dto;

public class PageRequestDto {

	public static Integer MAX_SIZE = 100;
	public static Integer DEFAULT_SIZE = 20;
	public static Integer DEFAULT_PAGE = 0;
	
	private Integer page;
	private Integer size;
	
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	
	@Override
	public String toString() {
		return "PageRequestDto [page=" + page + ", size=" + size + "]";
	}
	
	
}
