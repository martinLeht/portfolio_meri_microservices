package com.saitama.microservices.blogservice.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;

public abstract class UuidIdentified {

    @Id   
    protected UUID id;    

    public void setId(UUID id) {

        if (this.id != null) {
            throw new UnsupportedOperationException("ID is already defined");
        }

        this.id = id;
    }

    public UUID getId() {
    	return id;
    }
}
