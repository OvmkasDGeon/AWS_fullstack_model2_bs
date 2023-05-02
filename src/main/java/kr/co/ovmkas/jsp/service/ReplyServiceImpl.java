package kr.co.ovmkas.jsp.service;

import java.util.List;

import kr.co.ovmkas.jsp.dao.ReplyDao;
import kr.co.ovmkas.jsp.domain.Reply;



public class ReplyServiceImpl implements ReplyService{
	private ReplyDao dao = new ReplyDao();
	@Override
	public Long register(Reply reply) {
		return (long)dao.insert(reply);
		
	}

	@Override
	public List<Reply> list(Long bno) {
		// TODO Auto-generated method stub
		return dao.selectList(bno);
	}

	@Override
	public int remove(Long bno) {
		return dao.delete(bno);
	}

	@Override
	public Reply get(Long rno) {
		return dao.selectOne(rno);
	}

}
