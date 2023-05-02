package kr.co.ovmkas.jsp.board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.ovmkas.jsp.domain.Board;
import kr.co.ovmkas.jsp.domain.Criteria;
import kr.co.ovmkas.jsp.service.BoardService;
import kr.co.ovmkas.jsp.service.BoardServiceImpl;
import kr.co.ovmkas.jsp.util.ParamSolver;

@WebServlet("/board/modify")
public class BoardModifyController extends HttpServlet{
	private BoardService boardService = new BoardServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute("member")==null ) {
			resp.sendRedirect(req.getContextPath() + "/member/login");
			return ;
		}
		req.setAttribute("cri", ParamSolver.getParams(req, Criteria.class));
		req.setAttribute("board", boardService.get(Long.valueOf(req.getParameter("bno"))));//원래는 getParameter로 카테고리(1)를 처리한다 나중엔 카테로리도 처리해야된다
		req.getRequestDispatcher("/WEB-INF/jsp/board/modify.jsp").forward(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Criteria criteria = ParamSolver.getParams(req, Criteria.class);
		Board board = ParamSolver.getParams(req, Board.class); 
		boardService.modify(board);
		resp.sendRedirect("view?bno=" + board.getBno() +"&" + criteria.getFullQueryString());//list.jsp가 같은경로라서 list를쓴다@WebServlet을 따라간다
	}
	
}
