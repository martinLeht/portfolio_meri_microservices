CREATE TABLE IF NOT EXISTS comment (
	id bigint NOT NULL,
	uuid uuid NOT NULL UNIQUE,
	parent_id bigint,
	user_id uuid NOT NULL,
	post_id uuid NOT NULL,
	username VARCHAR(255) NOT NULL,
	content VARCHAR(255),
	created_at TIMESTAMP, 
	updated_at TIMESTAMP,
	verified BOOLEAN,
	PRIMARY KEY (id),
	CONSTRAINT fk_comment_parent FOREIGN KEY (parent_id) REFERENCES comment(id)
);


CREATE TABLE IF NOT EXISTS experience (
	id bigint NOT NULL,
	uuid uuid NOT NULL UNIQUE,
	user_id uuid NOT NULL,
	title VARCHAR(255),
	content VARCHAR(2000), 
	experience_type VARCHAR(255),
	short_description VARCHAR(255),
	hidden BOOLEAN, 
	start_date DATE,
	end_date DATE,
	created_at TIMESTAMP,
	updated_at TIMESTAMP,
	PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS media (
	id bigint NOT NULL, 
	name VARCHAR(255), 
	src VARCHAR(255), 
	type VARCHAR(255), 
	PRIMARY KEY (id),
	CONSTRAINT fk_media FOREIGN KEY (id) REFERENCES experience(id)
);
