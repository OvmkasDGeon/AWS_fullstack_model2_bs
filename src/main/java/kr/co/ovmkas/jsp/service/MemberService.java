package kr.co.ovmkas.jsp.service;

import java.util.List;

import kr.co.ovmkas.jsp.domain.Member;

public interface MemberService {
	//회원가입
	void register(Member member);
	//로그인
	int login(String id, String pw);//String으로 해도되고 Member member로 해도 된다
	//회원 단일 조회
	Member get(String id);
	//회원목록 조회
	List<Member> list();
	//회원 정보 수정
	void modify(Member member);
	void modifyPw(Member member);
	//탈퇴
	void remove(Member member);
}
