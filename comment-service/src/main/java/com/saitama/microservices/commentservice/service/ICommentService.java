package com.saitama.microservices.commentservice.service;

import java.util.UUID;

import com.saitama.microservices.commentservice.dto.CommentDTO;
import com.saitama.microservices.commonlib.service.IBaseReadWriteService;

public interface ICommentService extends IBaseReadWriteService<CommentDTO, UUID> {
	
}
