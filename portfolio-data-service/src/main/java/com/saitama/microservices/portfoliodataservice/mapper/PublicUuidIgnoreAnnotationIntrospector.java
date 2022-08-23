package com.saitama.microservices.portfoliodataservice.mapper;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.saitama.microservices.portfoliodataservice.annotation.PublicUuid;

public class PublicUuidIgnoreAnnotationIntrospector extends AnnotationIntrospector {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3209542920745255667L;

	@Override
    public Version version() {
        return new Version(1, 0, 0, "Ignore @PublicId", "group.id", "artifact.id");
    }

    @Override
    public boolean hasIgnoreMarker(AnnotatedMember m) {
        return hasIdAnnotation(m);
    }

    boolean hasIdAnnotation(AnnotatedMember member) {
        return member.getAnnotation(PublicUuid.class) != null;
    }


}
