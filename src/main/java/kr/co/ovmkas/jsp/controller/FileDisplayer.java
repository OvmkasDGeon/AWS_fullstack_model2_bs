package kr.co.ovmkas.jsp.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.ovmkas.jsp.dao.AttachDao;
import kr.co.ovmkas.jsp.domain.Attach;
import kr.co.ovmkas.jsp.util.ParamSolver;

@WebServlet("/display")
public class FileDisplayer extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fullPath = req.getParameter("fullpath");
		if(fullPath == null) return;
		File file = new File(ParamSolver.UPLOAD_PATH, fullPath);
		System.out.println(file);
	
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
		byte[] bytes = new byte[(int)file.length()];
		bis.read(bytes);
		BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream());
		bos.write(bytes);
		bis.close();
		bos.close();
		
	}
}
