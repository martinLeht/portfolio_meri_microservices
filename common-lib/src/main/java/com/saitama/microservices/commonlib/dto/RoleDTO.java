package com.saitama.microservices.commonlib.dto;

import java.util.Collection;

import com.saitama.microservices.commonlib.constant.Authority;

import lombok.Data;

@Data
public class RoleDTO {

    private Long id;
    private Authority authority;
    private Collection<PrivilegeDTO> privileges;
}
