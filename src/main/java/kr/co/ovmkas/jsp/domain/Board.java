package kr.co.ovmkas.jsp.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Board {
	private Long bno;
	
	
	private String title;
	private String content;
	private String writer;
	
	
	private Date regdate;
	private String updatedate;
	private int hitcount;
	private Integer category;
	
	private List<Attach> attachs = new ArrayList<Attach>();//첨부파일을 위한 List
	private int replyCnt;
}
