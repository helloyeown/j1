package org.zerock.j1.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.j1.domain.Board;
import org.zerock.j1.dto.BoardReadDTO;
import org.zerock.j1.repositories.search.BoardSearch;

public interface BoardRepository extends JpaRepository<Board, Long>, BoardSearch {
	
	@Query("select b from Board b where b.bno = :bno")
	BoardReadDTO readOne(@Param("bno") Long bno);

	List<Board> findByTitleContaining(String title);

	// JPQL
	@Query("select b from Board b where b.title like %:title%")
	List<Board> listTitle(@Param("title") String title);

	@Query("select b.bno, b.title from Board b where b.title like %:title%")
	List<Object[]> listTitle2(@Param("title") String title);

	@Query("select b.bno, b.title from Board b where b.title like %:title%")
	Page<Object[]> listTitle2(@Param("title") String title, Pageable pageable);

	@Modifying
	@Query("update Board b set b.title = :title where b.bno = :bno")
	int modifyTitle(@Param("title") String title, @Param("bno") Long bno);

	Page<Board> findByContentContaining(String content, Pageable pageable);

	// native Query
	@Query(value = "select * from t_board", nativeQuery = true)
	List<Object[]> listNative();

	@Query("select b.bno, b.title, b.writer, count(r) from Board b left outer join Reply r on r.board = b group by b order by b.bno desc")
	List<Object[]> getListWithRCnt();

}