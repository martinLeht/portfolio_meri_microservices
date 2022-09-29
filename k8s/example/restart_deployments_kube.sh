#!/bin/bash
#------------------
#@file:restart_deployments_kube.sh
#------------------

rollout_restart_deployments ()
{
	echo "Deploying images..."
	
	kubectl rollout restart deployment/api-gateway -n api-gateway
	kubectl rollout restart deployment/storage-service -n storage-service
	kubectl rollout restart deployment/blog-service -n blog-service
	kubectl rollout restart deployment/authentication-service -n authentication-service
	kubectl rollout restart deployment/portfolio-data-service -n portfolio-data-service
	
	echo "Services deployed to kubernetes!"
}
rollout_restart_deployments