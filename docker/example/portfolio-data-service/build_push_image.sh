#!/bin/bash
#------------------
#@file:build_push_image.sh
#------------------

portfolioDataServiceDir=portfolio-data-service

clean_up_image () 
{

	echo "Removing old images..."
	
	echo "Removing Portfolio Data Service image"
	docker rmi portfolio-data-service:0.0.1-SNAPSHOT
	docker rmi <CONTAINER_REGISTRY_HERE>/portfolio-portfolio-data-service:0.0.1-SNAPSHOT
	
	echo "All old images removed!"

}

build_image ()
{
	echo "Building image..."
	
	cd "../../$portfolioDataServiceDir"
	mvn clean spring-boot:build-image -DskipTests
	
	echo "All image built!"
	cd "../docker"
}

push_image_to_registry ()
{
	echo "Pushing image to registry"
	
	docker tag portfolio-data-service:0.0.1-SNAPSHOT <CONTAINER_REGISTRY_HERE>/portfolio-portfolio-data-service:0.0.1-SNAPSHOT
	docker push <CONTAINER_REGISTRY_HERE>/portfolio-portfolio-data-service:0.0.1-SNAPSHOT

}

clean_up_image
build_image
push_image_to_registry
