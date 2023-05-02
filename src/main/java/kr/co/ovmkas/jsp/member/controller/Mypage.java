package kr.co.ovmkas.jsp.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.ovmkas.jsp.domain.Member;
import kr.co.ovmkas.jsp.service.MemberService;
import kr.co.ovmkas.jsp.service.MemberServiceImpl;
import kr.co.ovmkas.jsp.util.ParamSolver;

@WebServlet("/member/mypage")
public class Mypage extends HttpServlet{
	private MemberService memberService = new MemberServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Object member = req.getSession().getAttribute("member");
		if(req.getSession().getAttribute("member")==null){
			resp.sendRedirect(req.getContextPath()+"/member/login");
			return ;
		}
		req.setAttribute("member", memberService.get(((Member)member).getId()));
		req.getRequestDispatcher("/WEB-INF/jsp/member/mypage.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("member")==null){
			resp.sendRedirect(req.getContextPath()+"/member/login");
			return ;
		}
		
		Member member = ParamSolver.getParams(req, Member.class);
		System.out.println(member);
		
		memberService.modify(member);
		req.getSession().setAttribute("member", member);
		resp.sendRedirect(req.getContextPath()+"/");
	}

}
