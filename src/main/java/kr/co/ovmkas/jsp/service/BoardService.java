package kr.co.ovmkas.jsp.service;

import java.util.List;

import kr.co.ovmkas.jsp.domain.Board;
import kr.co.ovmkas.jsp.domain.Criteria;

public interface BoardService {
	//CRUD
	Long register(Board board);
	
	Board get(Long bno);
	
	List<Board> list(Criteria cri);
	
	int listCount(Criteria cri);
	
	void modify(Board board);
	
	void remove(Long bno);
}
