package org.zerock.j1.repositories.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.QBoard;
import org.zerock.j1.domain.QReply;
import org.zerock.j1.dto.BoardListRCntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

	public BoardSearchImpl(){
		super(Board.class);		// 도메인
	}

	@Override
	public Page<Board> search1(String searchType, String keyword, Pageable pageable){

		QBoard board = QBoard.board;

		JPQLQuery<Board> query = from(board);

		if(keyword != null && searchType !=null){

			// tc -> [t, c]
			String[] searchArr = searchType.split("");	// 글자 하나씩 쪼갬

			// ( )
			BooleanBuilder searchBuilder = new BooleanBuilder();

			for (String type : searchArr) {
				switch (type) {
					case "t" -> searchBuilder.or(board.title.contains(keyword));
					case "c" -> searchBuilder.or(board.content.contains(keyword));
					case "w" -> searchBuilder.or(board.writer.contains(keyword));
				}
			}	//end for
			query.where(searchBuilder);
		} 

		this.getQuerydsl().applyPagination(pageable, query);

		List<Board> list = query.fetch();	// fetch: 목록 데이터를 실제로 가져옴
		long count = query.fetchCount();

		log.info(list);
		log.info(count);

		return new PageImpl<>(list, pageable, count);
	}

	@Override
	public Page<Object[]> searchWithRCnt(String searchType, String keyword, Pageable pageable) {
	
		QBoard board = QBoard.board;
		QReply reply = QReply.reply;

		JPQLQuery<Board> query = from(board);	// board 테이블로부터 쿼리를 만들겠다
		query.leftJoin(reply).on(reply.board.eq(board));	// join
		
		if(keyword != null && searchType !=null){

			// tc -> [t, c]
			String[] searchArr = searchType.split("");	// 글자 하나씩 쪼갬

			// ( )
			BooleanBuilder searchBuilder = new BooleanBuilder();

			for (String type : searchArr) {
				switch (type) {
					case "t" -> searchBuilder.or(board.title.contains(keyword));
					case "c" -> searchBuilder.or(board.content.contains(keyword));
					case "w" -> searchBuilder.or(board.writer.contains(keyword));
				}
			}	//end for
			query.where(searchBuilder);
		} 
		
		query.groupBy(board);	// board별로 group by, 순서를 알아서 generation 해줌 (순서 상관 X)
		
		JPQLQuery<Tuple> tupleQuery = query.select(board.bno, board.title, board.writer, reply.countDistinct());

		// fetch 하기 전에 페이징 처리
		this.getQuerydsl().applyPagination(pageable, tupleQuery);

		log.info("1------------------");
		List<Tuple> tuples = tupleQuery.fetch();

		List<Object[]> arrList = tuples.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());

		log.info("2------------------");
		log.info(tuples);

		log.info("3------------------");
		long count = tupleQuery.fetchCount();
		log.info("count: " + count);

		return new PageImpl<>(arrList, pageable, count);

	}

	@Override
	public PageResponseDTO<BoardListRCntDTO> searchDTORcnt(PageRequestDTO dto) {

		Pageable pageable = makePageable(dto);

		QBoard board = QBoard.board;
		QReply reply = QReply.reply;

		JPQLQuery<Board> query = from(board);	// board 테이블로부터 쿼리를 만들겠다
		query.leftJoin(reply).on(reply.board.eq(board));	// join
		
		String keyword = dto.getKeyword();
		String searchType = dto.getType();

		if(keyword != null && searchType !=null){

			// tc -> [t, c]
			String[] searchArr = searchType.split("");	// 글자 하나씩 쪼갬

			// ( )
			BooleanBuilder searchBuilder = new BooleanBuilder();

			for (String type : searchArr) {
				switch (type) {
					case "t" -> searchBuilder.or(board.title.contains(keyword));
					case "c" -> searchBuilder.or(board.content.contains(keyword));
					case "w" -> searchBuilder.or(board.writer.contains(keyword));
				}
			}	//end for
			query.where(searchBuilder);
		} 
		
		this.getQuerydsl().applyPagination(pageable, query);
		query.groupBy(board);	// board별로 group by, 순서를 알아서 generation 해줌 (순서 상관 X)

		JPQLQuery<BoardListRCntDTO> listQuery = query.select(Projections.bean(BoardListRCntDTO.class, 
				board.bno, board.title, board.writer, reply.countDistinct().as("replyCount")));

		List<BoardListRCntDTO> list = listQuery.fetch();

		log.info("-----------------");
		log.info(list);

		long totalCount = listQuery.fetchCount();

		return new PageResponseDTO<>(list, totalCount, dto);

	}
	
}
