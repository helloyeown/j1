package org.zerock.j1.service;

import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.ReplyDTO;
import org.zerock.j1.dto.ReplyPageRequestDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface ReplyService {
	
	// 목록
	PageResponseDTO<ReplyDTO> list(ReplyPageRequestDTO dto);

	// 등록
	Long register(ReplyDTO replyDTO);

	// 조회
	ReplyDTO read(Long rno);

	// 삭제
	void remove(Long rno);

	// 수정
	void modify(ReplyDTO replyDTO);

}
