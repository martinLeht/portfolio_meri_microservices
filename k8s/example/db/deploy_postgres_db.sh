#!/bin/bash
#------------------
#@file:deploy_postgres_db.sh
#------------------


init_namespace_reader_cluster_role ()
{
	kubectl delete clusterrole microservices-kubernetes-namespace-reader
	kubectl apply -f rbac-cluster-role.yaml -n default
}

init_namespaces () 
{
	kubectl delete namespace postgres
	kubectl create namespace postgres
}

init_service_accounts ()
{	
	kubectl create serviceaccount portfolio-microservices-service-account -n postgres
}

init_cluster_roles ()
{
	kubectl delete clusterrolebinding service-pod-reader-postgres
	kubectl create clusterrolebinding service-pod-reader-postgres --clusterrole=microservices-kubernetes-namespace-reader --serviceaccount=postgres:portfolio-microservices-service-account
	
}

init_configmaps ()
{
	echo "Initializing ConfigMaps..."
	kubectl apply -n postgres -f postgres-configmap.yaml
}

init_secrets ()
{
	echo "Initializing Secrets..."
	kubectl apply -n postgres -f postgres-credentials.yaml
	kubectl apply -n postgres -f postgres-root-credentials.yaml
}

deploy_to_kubernetes ()
{
	echo "Deploying images..."
	kubectl apply -n postgres -f postgres.yaml
	
	echo "Services deployed to kubernetes!"
}

init_namespace_reader_cluster_role
init_namespaces
init_service_accounts
init_cluster_roles
init_configmaps
init_secrets
deploy_to_kubernetes
