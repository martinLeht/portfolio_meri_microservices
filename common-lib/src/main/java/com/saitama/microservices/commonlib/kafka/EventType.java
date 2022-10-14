package com.saitama.microservices.commonlib.kafka;


public enum EventType {

	EMAIL_EVENT("supplier-out-0", "emailSendConsumer-in-0");
	
	private String supplierBinding;
	private String consumerBinding;
	
	EventType(String supplierBinding, String consumerBinding) {
		this.supplierBinding = supplierBinding;
		this.consumerBinding = consumerBinding;
	}
	
	public String getSupplierBinding() {
		return supplierBinding;
	}
	
	public String getConsumerBinding() {
		return consumerBinding;
	}
}
