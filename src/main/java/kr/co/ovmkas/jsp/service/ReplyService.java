package kr.co.ovmkas.jsp.service;

import java.util.List;

import kr.co.ovmkas.jsp.domain.Board;
import kr.co.ovmkas.jsp.domain.Criteria;
import kr.co.ovmkas.jsp.domain.Reply;

public interface ReplyService {
	//CRUD
	Long register(Reply reply);
	
	List<Reply> list(Long bno);
	
	Reply get(Long rno);
	
	int remove(Long bno);
	
}
