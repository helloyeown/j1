package org.zerock.j1.repository;

import java.util.Optional;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.j1.domain.Sample;
import org.zerock.j1.repositories.SampleRepository;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class SampleRepositoryTest {
	
	@Autowired
	private SampleRepository sampleRepository;

	@Test
	public void test1() {
		log.info(sampleRepository.getClass().getName());
	}

	@Test
	public void testInsert() {
		IntStream.rangeClosed(1, 100).forEach(i -> {
			Sample obj = Sample.builder()
				.keyCol("u"+i)
				.first("first")
				.last("last"+i)
				.build();

				sampleRepository.save(obj);
		});
	}

	@Test
	public void testRead(){
		String keyCol = "u10";

		Optional<Sample> result = sampleRepository.findById(keyCol);

		Sample obj = result.orElseThrow();		// 결과 없으면 예외처리
		log.info(obj);
	}

	@Test
	public void testDelete(){
		String keyCol = "u1";

		sampleRepository.deleteById(keyCol);
	}

	@Test
	public void testPaging(){
		Pageable pageable = PageRequest.of(0, 10, Sort.by("keyCol").descending());
		// 페이지는 0부터 시작(주의)

		Page<Sample> result = sampleRepository.findAll(pageable);
		// Pageable의 리턴 타입은 항상 Page

		log.info(result.getTotalElements());	// 데이터 갯수
		log.info(result.getTotalPages());			// 몇 페이지인지

		// result.hasNext();		// 다음 페이지 있는지
		// result.hasPrevious()	// 이전 페이지 있는지

		result.getContent().forEach(obj -> log.info(obj));	// 내용물(리스트)
		
	}

}
