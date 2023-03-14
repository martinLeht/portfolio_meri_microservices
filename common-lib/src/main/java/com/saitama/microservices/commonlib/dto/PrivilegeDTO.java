package com.saitama.microservices.commonlib.dto;

import com.saitama.microservices.commonlib.constant.PrivilegeType;

import lombok.Data;


@Data
public class PrivilegeDTO {

	private Long id;
	private PrivilegeType name;
}
