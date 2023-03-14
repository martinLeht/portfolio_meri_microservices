package com.saitama.microservices.portfoliodataservice.service.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saitama.microservices.commonlib.dto.PageRequestDTO;
import com.saitama.microservices.commonlib.dto.PaginationDTO;
import com.saitama.microservices.commonlib.exception.EntityNotFoundException;
import com.saitama.microservices.portfoliodataservice.dto.CommentDTO;
import com.saitama.microservices.portfoliodataservice.entity.Comment;
import com.saitama.microservices.portfoliodataservice.mapper.CommentMapper;
import com.saitama.microservices.portfoliodataservice.repository.CommentRepository;
import com.saitama.microservices.portfoliodataservice.service.ICommentService;
import com.saitama.microservices.portfoliodataservice.util.QueryUtils;


@Service
public class CommentServiceImpl implements ICommentService {
	
	private static final Logger LOG = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	private final CommentRepository commentRepository;
	private final CommentMapper commentMapper;
	
	
	@Autowired
	public CommentServiceImpl(CommentRepository commentRepository,
							  CommentMapper commentMapper) {
		this.commentRepository = commentRepository;
		this.commentMapper = commentMapper;
	}
	
	
	@Override
	public PaginationDTO<CommentDTO> getPaginated(PageRequestDTO pageDto) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PaginationDTO<CommentDTO> getPaginatedPostComments(UUID postId, PageRequestDTO pageDto) {
		Pageable sortedByCreatedAt = QueryUtils.createPageRequestSortedByField("createdAt", pageDto);		
		Page<Comment> comments = commentRepository.findByPostIdAndVerified(postId, true, sortedByCreatedAt);
		
		List<CommentDTO> commentDtos = comments.stream()
				.map(commentMapper::toDto)
				.collect(Collectors.toList());
		
		if (comments.getNumber() + 1 < comments.getTotalPages()) {
			return PaginationDTO.<CommentDTO>builder()
					.page(comments.getNumber())
					.pageSize(comments.getSize())
					.totalSize(comments.getTotalElements())
					.nextPage(comments.getNumber() + 1)
					.data(commentDtos)
					.build();
		} else {
			return PaginationDTO.<CommentDTO>builder()
					.page(comments.getNumber())
					.pageSize(comments.getSize())
					.totalSize(comments.getTotalElements())
					.data(commentDtos)
					.build();
		}
	}
	
	@Override
	public PaginationDTO<CommentDTO> getPaginatedThreadComments(UUID parentId, PageRequestDTO pageDto) {
		Pageable sortedByCreatedAt = QueryUtils.createPageRequestSortedByField("createdAt", pageDto);
		Page<Comment> comments = commentRepository.findCommentsByParentIdAndVerifiedWithPagination(parentId.toString(), true, sortedByCreatedAt);
		
		List<CommentDTO> commentDtos = comments.stream()
				.map(commentMapper::toDto)
				.collect(Collectors.toList());

		if (comments.getNumber() + 1 < comments.getTotalPages()) {
			return PaginationDTO.<CommentDTO>builder()
					.page(comments.getNumber())
					.pageSize(comments.getSize())
					.totalSize(comments.getTotalElements())
					.nextPage(comments.getNumber() + 1)
					.data(commentDtos)
					.build();
		} else {
			return PaginationDTO.<CommentDTO>builder()
					.page(comments.getNumber())
					.pageSize(comments.getSize())
					.totalSize(comments.getTotalElements())
					.data(commentDtos)
					.build();
		}
	}

	@Override
	public CommentDTO getById(UUID id) {
		Comment comment = commentRepository.findByUuid(id).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("comment-not-found", "Comment not found with uuid: " + id));
		return commentMapper.toDto(comment);
	}

	@Override
	public CommentDTO create(CommentDTO commentDto) {
		
		Comment comment = commentMapper.fromDto(commentDto);
		comment.setVerified(true);
		
		UUID uuid = UUID.randomUUID();
		List<Comment> existingComments = commentRepository.findByUuid(uuid);
		
		if (existingComments.isEmpty()) {
			comment.setUuid(uuid);
		} else {
			comment.setUuid(UUID.randomUUID());
		}
		
		Comment saved = commentRepository.save(comment);
		
		return commentMapper.toDto(saved);
	}

	@Override
	public CommentDTO update(UUID id, CommentDTO commentDto) {
		Comment comment = this.commentRepository.findByUuid(id).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("comment-not-found", "Comment not found with uuid: " + id));
		
		comment.setContent(commentDto.getContent());
		
		Comment saved = commentRepository.save(comment);	
		return commentMapper.toDto(saved);
	}

	@Override
	public void delete(UUID id) {
		Comment comment = this.commentRepository.findByUuid(id).stream()
				.findFirst()
				.orElseThrow(() -> new EntityNotFoundException("comment-not-found", "Comment not found with uuid: " + id));
		commentRepository.delete(comment);
		
	}
	
}
