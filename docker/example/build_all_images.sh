#!/bin/bash
#------------------
#@file:build_images.sh
#------------------

apiGatewayDir=api-gateway
storageServiceDir=storage-service
blogServiceDir=blog-service
authServiceDir=authentication-service
portfolioDataServiceDir=portfolio-data-service

build_and_push_images ()
{
	echo "Building images..."
	
	cd "$apiGatewayDir"
	sh build_push_image.sh
	cd ".."
	
	cd "$storageServiceDir"
	sh build_push_image.sh
	cd ".."
	
	cd "$blogServiceDir"
	sh build_push_image.sh
	cd ".."
	
	cd "$authServiceDir"
	sh build_push_image.sh
	cd ".."

	cd "$portfolioDataServiceDir"
	sh build_push_image.sh
	cd ".."
	
	echo "All images built!"
}
build_and_push_images
