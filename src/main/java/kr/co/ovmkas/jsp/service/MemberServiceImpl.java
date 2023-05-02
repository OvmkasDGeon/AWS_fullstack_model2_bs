package kr.co.ovmkas.jsp.service;

import java.util.List;

import org.mindrot.bcrypt.BCrypt;

import kr.co.ovmkas.jsp.dao.BoardDao;
import kr.co.ovmkas.jsp.dao.MemberDao;
import kr.co.ovmkas.jsp.dao.ReplyDao;
import kr.co.ovmkas.jsp.domain.Member;

public class MemberServiceImpl implements MemberService{
	private MemberDao memberDao = new MemberDao();
	private BoardDao boardDao= new BoardDao();
	private ReplyDao replyDao = new ReplyDao();
	@Override
	public void register(Member member) {
		member.setPw(BCrypt.hashpw(member.getPw(), BCrypt.gensalt()));
		memberDao.insert(member);
	}

	@Override
	public int login(String id, String pw) {
		Member member = memberDao.selectOne(id);
		if (member == null) {
			// 로그인 실패(아이디 없음)
			return 2;
		} else if (! BCrypt.checkpw(pw, member.getPw())) {
			//로그인 실패 비밀번호 다름
			return 3;
		} else {
			//로그인성공
			return 1;
		}
	}

	@Override
	public Member get(String id) {
		// TODO Auto-generated method stub
		return memberDao.selectOne(id);
	}

	@Override
	public List<Member> list() {
		// TODO Auto-generated method stub
		return memberDao.selectList();
	}

	@Override
	public void modify(Member member) {
		// TODO Auto-generated method stub
		memberDao.update(member);
		
	}

	@Override
	public void modifyPw(Member member) {
		//암호화 처리
		member.setPw(BCrypt.hashpw(member.getPw(), BCrypt.gensalt()));
		// DB반영
		memberDao.updatePw(member);
	}

	@Override
	public void remove(Member member) {
		//게시글 처리
		boardDao.updateWriterToNull(member.getId());
		//댓글 처리
		replyDao.updateWriterToNull(member.getId());
		//회원탈퇴
		memberDao.delete(member);
	}
}
