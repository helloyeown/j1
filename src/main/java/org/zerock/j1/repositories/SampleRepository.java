package org.zerock.j1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.j1.domain.Sample;

public interface SampleRepository extends JpaRepository<Sample, String> {	// String은 pk 타입
	
}
