package org.zerock.j1.service;

import org.zerock.j1.dto.BoardDTO;
import org.zerock.j1.dto.BoardListRCntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface BoardService {
	
	PageResponseDTO<BoardListRCntDTO> listRCnt(PageRequestDTO pageRequestDTO);

	BoardDTO getOne(Long bno);

}
