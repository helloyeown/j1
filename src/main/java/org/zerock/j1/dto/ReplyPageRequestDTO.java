package org.zerock.j1.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReplyPageRequestDTO extends PageRequestDTO {
	
	private Long bno;
	
	@Builder.Default
	private int page = 1;
	
	@Builder.Default
	private int size = 10;

	private boolean last;
}
