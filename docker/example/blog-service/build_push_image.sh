#!/bin/bash
#------------------
#@file:build_push_image.sh
#------------------

blogServiceDir=blog-service

clean_up_image () 
{

	echo "Removing old images..."
	
	echo "Removing Blog Service image"
	docker rmi blog-service:0.0.1-SNAPSHOT
	docker rmi <CONTAINER_REGISTRY_HERE>/portfolio-blog-service:0.0.1-SNAPSHOT
	
	echo "All old images removed!"

}

build_image ()
{
	echo "Building image..."
	
	cd "../../$blogServiceDir"
	mvn clean spring-boot:build-image -DskipTests
	
	echo "All image built!"
	cd "../scripts"
}

push_image_to_registry ()
{
	echo "Pushing image to registry"
	
	docker tag blog-service:0.0.1-SNAPSHOT rg.nl-ams.scw.cloud/portfolio-microservices-container-registry/portfolio-blog-service:0.0.1-SNAPSHOT
	docker push rg.nl-ams.scw.cloud/portfolio-microservices-container-registry/portfolio-blog-service:0.0.1-SNAPSHOT

}

clean_up_image
build_image
push_image_to_registry
