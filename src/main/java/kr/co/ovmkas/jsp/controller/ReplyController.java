package kr.co.ovmkas.jsp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kr.co.ovmkas.jsp.domain.Reply;
import kr.co.ovmkas.jsp.service.ReplyService;
import kr.co.ovmkas.jsp.service.ReplyServiceImpl;
import kr.co.ovmkas.jsp.util.ParamSolver;

@WebServlet("/reply")
public class ReplyController extends HttpServlet{
	private ReplyService service = new ReplyServiceImpl();
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		System.out.println(req.getParameter("rno"));
//		System.out.println("dodelete()");
		//로그인 여부
		//작성댓글과 로그인이 동일한지
		Long rno = Long.valueOf(req.getParameter("rno"));

		PrintWriter out = resp.getWriter(); 		//out객체
		Reply reply = service.get(rno);
		//댓글이 삭제 됐는지 여부를 out객체를 통해 print해준다
		if(reply != null && ParamSolver.isMine(req, reply.getWriter())) {
			out.print(service.remove(rno));
		}else {
			out.print(0);
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Long bno = Long.valueOf(req.getParameter("bno"));
		List<Reply> replies = service.list(bno);
//		System.out.println(replies);
		Gson gson = new Gson();
//		gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		String json = gson.toJson(replies);
//		System.out.println(json);
		resp.setContentType("application/json; charset=utf8;");
		resp.getWriter().print(json);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Reply reply = ParamSolver.getParams(req, Reply.class);
//		System.out.println(reply);
//		System.out.println("dopost()");
		service.register(reply);
	}
	//Http Method
	//GET, POST PUT(PATCH부분수정), DELETE
	
}
