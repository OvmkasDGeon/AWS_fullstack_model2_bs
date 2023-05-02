package kr.co.ovmkas.jsp.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@WebServlet("/file")
public class FileController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("file.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MultipartRequest multipartRequest  = new MultipartRequest(req, "C:\\upload", 5*1024*1024, "utf-8", 
				new DefaultFileRenamePolicy());
		//cos의 단점 : 여러개 파일이 전송은 가능하지만 확인은 할 수 없다
		String id = multipartRequest.getParameter("id");
		String file = multipartRequest.getParameter("file");
		System.out.println(id);
		String origin = multipartRequest.getOriginalFileName("file");//파일전송에서 나온 file 이름
		String realName = multipartRequest.getFilesystemName("file");//c:upload에 저장된 이름
//		multipartRequest.getFile("file").
		System.out.println(origin);
		System.out.println(realName);
//		System.out.println(file);
		
		
	}

}
