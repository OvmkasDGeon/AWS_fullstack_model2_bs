package kr.co.ovmkas.jsp.board.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.ovmkas.jsp.domain.Board;
import kr.co.ovmkas.jsp.service.BoardService;
import kr.co.ovmkas.jsp.service.BoardServiceImpl;
import kr.co.ovmkas.jsp.util.ParamSolver;

//static 추가 해야됨
import static kr.co.ovmkas.jsp.util.ParamSolver.*;

@WebServlet("/board/write")
@MultipartConfig(location = ParamSolver.UPLOAD_PATH, fileSizeThreshold = 8 * 1024) 
public class BoardWriteController extends HttpServlet {
	private BoardService boardService = new BoardServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println(req.getContentType());
//		System.out.println(req.getRequestURI());//현재 주소
		
		if (!isLogin(req)) {
			//글쓰기 시 로그인이 안됐을 때 로그인 화면을 보여주고 바로 글쓰기 화면으로 넘어가는 것
			resp.sendRedirect(req.getContextPath() + "/member/login?href=" + URLEncoder.encode(req.getRequestURI()+"?"+ req.getQueryString(), "utf-8"));
			
			return;
		}
		List<String> categories = new ArrayList<>(Arrays.asList("공지사항","자유게시판","자료실","갤러리"));
		req.setAttribute("categories", categories);
		req.getRequestDispatcher("/WEB-INF/jsp/board/write.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		Board board = new Board(req.getParameter("title"), req.getParameter("content"), req.getParameter("writer"));
//		System.out.println(req.getContentType());
		if (!isLogin(req)) {
			//글쓰기 시 로그인이 안됐을 때 로그인 화면을 보여주고 바로 글쓰기 화면으로 넘어가는 것
			resp.sendRedirect(req.getContextPath() + "/member/login?href=" + URLEncoder.encode(req.getRequestURI()+"?" + req.getQueryString(), "utf-8"));
			return;
		}
		Board board = getParams(req, Board.class);
		System.out.println(board);
		boardService.register(board);
		resp.sendRedirect("list?category=" + board.getCategory() );// list.jsp가 같은경로라서 list를쓴다@WebServlet을 따라간다
	}

}
