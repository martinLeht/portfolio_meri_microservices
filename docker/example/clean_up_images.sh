#!/bin/bash
#------------------
#@file:clean_up_images.sh
#------------------

remove_old_images () 
{
	echo "Removing old images..."
	
	echo "Removing Api Gateway image"
	docker rmi api-gateway:0.0.1-SNAPSHOT
	docker rmi <CONTAINER_REGISTRY_HERE>/portfolio-api-gateway:0.0.1-SNAPSHOT
	
	echo "Removing Storage Service image"
	docker rmi storage-service:0.0.1-SNAPSHOT
	docker rmi <CONTAINER_REGISTRY_HERE>/portfolio-storage-service:0.0.1-SNAPSHOT
	
	echo "Removing Blog Service image"
	docker rmi blog-service:0.0.1-SNAPSHOT
	docker rmi <CONTAINER_REGISTRY_HERE>/portfolio-blog-service:0.0.1-SNAPSHOT

	echo "Removing Authentication Service image"
	docker rmi authentication-service:0.0.1-SNAPSHOT
	docker rmi <CONTAINER_REGISTRY_HERE>/portfolio-authentication-service:0.0.1-SNAPSHOT

	echo "Removing Authentication Service image"
	docker rmi portfolio-data-service:0.0.1-SNAPSHOT
	docker rmi <CONTAINER_REGISTRY_HERE>/portfolio-portfolio-data-service:0.0.1-SNAPSHOT
	
	echo "All old images removed!"
}

remove_old_images