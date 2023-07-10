package org.zerock.j1.dto;

import lombok.Data;

@Data
public class ReplyDTO {
	
	private Long rno;

	private String replyText;

	private String replyFile;

	private String replyer;

	private Long bno;

}
