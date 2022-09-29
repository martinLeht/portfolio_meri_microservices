#!/bin/bash
#------------------
#@file:build_push_image.sh
#------------------

apiGatewayDir=api-gateway

clean_up_image () 
{

	echo "Removing old images..."
	
	echo "Removing Api Gateway image"
	docker rmi api-gateway:0.0.1-SNAPSHOT
	docker rmi <CONTAINER_REGISTRY_HERE>/portfolio-api-gateway:0.0.1-SNAPSHOT
	
	echo "All old images removed!"

}

build_image ()
{
	echo "Building image..."
	
	cd "../../$apiGatewayDir"
	mvn clean spring-boot:build-image -DskipTests
	
	echo "Image built!"
	cd "../scripts"
}

push_image_to_registry ()
{
	echo "Pushing image to registry"
	
	docker tag api-gateway:0.0.1-SNAPSHOT <CONTAINER_REGISTRY_HERE>/portfolio-api-gateway:0.0.1-SNAPSHOT
	docker push <CONTAINER_REGISTRY_HERE>/portfolio-api-gateway:0.0.1-SNAPSHOT

}

clean_up_image
build_image
push_image_to_registry
