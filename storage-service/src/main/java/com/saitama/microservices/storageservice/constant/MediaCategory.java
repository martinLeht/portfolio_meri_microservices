package com.saitama.microservices.storageservice.constant;

public enum MediaCategory {
	UI("ui_media"), GENERAL("general_media"), BLOG("blog_media"), EXPERIENCE("experience_media");
	
	public final String categoryName;
	
	MediaCategory(String categoryName) {
		this.categoryName = categoryName;
	}
}
