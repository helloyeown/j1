package org.zerock.j1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "t_reply")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")	// 조심해서 쓸 것
public class Reply {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long rno;

	private String replyText;

	private String replyFile;

	private String replyer;

	@ManyToOne(fetch = FetchType.LAZY)	// 연관관계를 밝히지 않으면 에러
	private Board board;


	public void changeText(String text){
		this.replyText = text;
	}

	public void changeFile(String fileName){
		this.replyFile = fileName;
	}

}
