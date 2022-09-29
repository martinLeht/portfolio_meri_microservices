#!/bin/bash
#------------------
#@file:deploy_kube.sh
#------------------

kubeDir=deployment
sslDir=ssl


init_namespace_reader_cluster_role ()
{
	cd "$kubeDir"
	kubectl delete clusterrole microservices-kubernetes-namespace-reader
	kubectl apply -f rbac-cluster-role.yaml -n default
	cd ".."
}

init_namespaces () 
{

	kubectl delete namespace api-gateway
	kubectl delete namespace blog-service
	kubectl delete namespace storage-service
	kubectl delete namespace authentication-service
	kubectl delete namespace portfolio-data-service

	kubectl create namespace api-gateway
	kubectl create namespace blog-service
	kubectl create namespace storage-service
	kubectl create namespace authentication-service
	kubectl create namespace portfolio-data-service
}

init_service_accounts ()
{	
	kubectl create serviceaccount portfolio-microservices-service-account -n api-gateway
	kubectl create serviceaccount portfolio-microservices-service-account -n blog-service
	kubectl create serviceaccount portfolio-microservices-service-account -n storage-service
	kubectl create serviceaccount portfolio-microservices-service-account -n authentication-service
	kubectl create serviceaccount portfolio-microservices-service-account -n portfolio-data-service
}

init_cluster_roles ()
{
	kubectl delete clusterrolebinding service-pod-reader-api-gateway
	kubectl delete clusterrolebinding service-pod-reader-blog-service
	kubectl delete clusterrolebinding service-pod-reader-storage-service
	kubectl delete clusterrolebinding service-pod-reader-authentication-service
	kubectl delete clusterrolebinding service-pod-reader-portfolio-data-service

	kubectl create clusterrolebinding service-pod-reader-api-gateway --clusterrole=microservices-kubernetes-namespace-reader --serviceaccount=api-gateway:portfolio-microservices-service-account
	kubectl create clusterrolebinding service-pod-reader-blog-service --clusterrole=microservices-kubernetes-namespace-reader --serviceaccount=blog-service:portfolio-microservices-service-account
	kubectl create clusterrolebinding service-pod-reader-storage-service --clusterrole=microservices-kubernetes-namespace-reader --serviceaccount=storage-service:portfolio-microservices-service-account
	kubectl create clusterrolebinding service-pod-reader-authentication-service --clusterrole=microservices-kubernetes-namespace-reader --serviceaccount=authentication-service:portfolio-microservices-service-account
	kubectl create clusterrolebinding service-pod-reader-portfolio-data-service --clusterrole=microservices-kubernetes-namespace-reader --serviceaccount=portfolio-data-service:portfolio-microservices-service-account
	
}

init_configmaps ()
{
	echo "Initializing ConfigMaps..."
	
	cd "$kubeDir"
	kubectl apply -n api-gateway -f api-gateway-configmap.yaml
	kubectl apply -n storage-service -f storage-service-configmap.yaml
	kubectl apply -n blog-service -f blog-service-configmap.yaml
	kubectl apply -n authentication-service -f authentication-service-configmap.yaml
	kubectl apply -n portfolio-data-service -f portfolio-data-service-configmap.yaml
	
	cd ".."
}

init_secrets ()
{
	echo "Initializing Secrets..."
	
	cd "$kubeDir"
	kubectl apply -n authentication-service -f authentication-service-credentials.yaml
	kubectl apply -n portfolio-data-service -f portfolio-data-service-credentials.yaml
	
	kubectl create secret docker-registry registry-secret --namespace api-gateway --docker-server=rg.nl-ams.scw.cloud --docker-username=portfolio-microservices-container-registry --docker-password=2bff34ca-1ca4-41c8-898e-9493f94e3dcd
	kubectl create secret docker-registry registry-secret --namespace blog-service --docker-server=rg.nl-ams.scw.cloud --docker-username=portfolio-microservices-container-registry --docker-password=2bff34ca-1ca4-41c8-898e-9493f94e3dcd
	kubectl create secret docker-registry registry-secret --namespace authentication-service --docker-server=rg.nl-ams.scw.cloud --docker-username=portfolio-microservices-container-registry --docker-password=2bff34ca-1ca4-41c8-898e-9493f94e3dcd
	kubectl create secret docker-registry registry-secret --namespace storage-service --docker-server=rg.nl-ams.scw.cloud --docker-username=portfolio-microservices-container-registry --docker-password=2bff34ca-1ca4-41c8-898e-9493f94e3dcd
	kubectl create secret docker-registry registry-secret --namespace portfolio-data-service --docker-server=rg.nl-ams.scw.cloud --docker-username=portfolio-microservices-container-registry --docker-password=2bff34ca-1ca4-41c8-898e-9493f94e3dcd
	
	cd "../$sslDir"
	kubectl create secret tls portfolio-microservices-tls --namespace api-gateway --key nippon-ssl.key --cert nippon-ssl.crt
	
	cd ".."
}

deploy_to_kubernetes ()
{
	echo "Deploying images..."
	
	cd "$kubeDir"
	kubectl apply -n api-gateway -f api-gateway.yaml
	kubectl apply -n api-gateway -f gateway-ingress.yaml	
	kubectl apply -n storage-service -f storage-service.yaml
	kubectl apply -n blog-service -f blog-service.yaml
	kubectl apply -n authentication-service -f authentication-service.yaml 
	kubectl apply -n portfolio-data-service -f portfolio-data-service.yaml
	cd ".."
	
	echo "Services deployed to kubernetes!"
}

init_namespace_reader_cluster_role
init_namespaces
init_service_accounts
init_cluster_roles
init_configmaps
init_secrets
deploy_to_kubernetes
