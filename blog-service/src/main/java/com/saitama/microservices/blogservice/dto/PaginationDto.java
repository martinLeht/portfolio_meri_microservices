package com.saitama.microservices.blogservice.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class PaginationDto<T> {

	private List<T> data;
	private int page;
	private int pageSize;
	private long totalSize;
	
	@JsonInclude(Include.NON_NULL)
	private Integer nextPage;
	
	
	public PaginationDto(int page, int pageSize, long totalSize, Integer nextPage, List<T> data) {
		this.page = page;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.nextPage = nextPage;
		this.data = data;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public long getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(long totalSize) {
		this.totalSize = totalSize;
	}
	public Integer getNextPage() {
		return nextPage;
	}
	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((nextPage == null) ? 0 : nextPage.hashCode());
		result = prime * result + page;
		result = prime * result + pageSize;
		result = prime * result + (int) (totalSize ^ (totalSize >>> 32));
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PaginationDto other = (PaginationDto) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (nextPage == null) {
			if (other.nextPage != null)
				return false;
		} else if (!nextPage.equals(other.nextPage))
			return false;
		if (page != other.page)
			return false;
		if (pageSize != other.pageSize)
			return false;
		if (totalSize != other.totalSize)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PaginationDto [page=" + page + ", pageSize=" + pageSize + ", totalSize=" + totalSize + ", nextPage=" + nextPage
				+ ", data=" + data + "]";
	}
	
	
}
