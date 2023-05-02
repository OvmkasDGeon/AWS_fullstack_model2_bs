package kr.co.ovmkas.jsp.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply {
	private Long rno; 
	private String content;
	private Date regDate;
	private String writer; 
	private Long bno;
	
	
	public Reply(String content, String writer, Long bno) {
		this.content = content;
		this.writer = writer;
		this.bno = bno;
	}
	
	
}
