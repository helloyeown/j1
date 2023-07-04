package org.zerock.j1.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_todo2")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Todo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)		// AI
	private Long tno;		// 반드시 객체자료형 사용

	@Column(length = 300, nullable = false)		// not null
	private String title;

	public void changeTitle(String title){
		this.title = title;
	}

}
