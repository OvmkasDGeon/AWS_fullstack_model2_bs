package kr.co.ovmkas.jsp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.ovmkas.jsp.domain.Board;
import kr.co.ovmkas.jsp.domain.Criteria;
import kr.co.ovmkas.jsp.service.BoardService;
import kr.co.ovmkas.jsp.service.BoardServiceImpl;

@WebServlet("/index")
public class Index extends HttpServlet{
	private BoardService boardService = new BoardServiceImpl();
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//게시글 목록
		Criteria cri = new Criteria(1, 5);
		List<String> categories = new ArrayList<>(Arrays.asList("공지사항","자유게시판","자료실","갤러리"));
		Map<String, List<Board>> lists = new LinkedHashMap<>();
		
		for(int i=1; i<=categories.size(); i++){
			cri.setCategory(i);
			lists.put(categories.get(i-1), boardService.list(cri));
		}
		req.setAttribute("lists", lists);
		
		//댓글 목록
		
		req.getRequestDispatcher("WEB-INF/jsp/index.jsp").forward(req, resp);
	}

}
