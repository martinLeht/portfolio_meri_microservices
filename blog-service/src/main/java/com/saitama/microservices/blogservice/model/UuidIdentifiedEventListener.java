package com.saitama.microservices.blogservice.model;


import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;

import com.saitama.microservices.commonlib.util.UuidUtils;

public class UuidIdentifiedEventListener extends AbstractMongoEventListener<UuidIdentified> {
    
    @Override
    public void onBeforeConvert(BeforeConvertEvent<UuidIdentified> event) {
        
        super.onBeforeConvert(event);
        UuidIdentified entity = event.getSource();
        
        if (entity.getId() == null) {
            entity.setId(UuidUtils.generateType4UUID());
        } 
    }    
}
