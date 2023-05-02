package kr.co.ovmkas.jsp.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import kr.co.ovmkas.jsp.domain.Attach;
import kr.co.ovmkas.jsp.util.ParamSolver;
import net.coobird.thumbnailator.Thumbnailator;

//               파일이 N개의 대한 전체 사이즈   ,  파일 한개당 사이즈           ,               위치     , 
/*@MultipartConfig(maxRequestSize = 10 * 1024 * 1024, maxFileSize = 5 * 1024
		* 1024, location = ParamSolver.UPLOAD_PATH, fileSizeThreshold = 8 * 1024)*/
@MultipartConfig(location = ParamSolver.UPLOAD_PATH, fileSizeThreshold = 8 * 1024 )
//maxRequestSize, MaxFileSize는 생략할 수 있다. (최대 크기를 가짐)

@WebServlet("/fileUpload")
public class FileUploadController extends HttpServlet {

	// @MultipartConfig
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("file.jsp").forward(req, resp);
	}

	/**
	 *
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(req.getContentType());

		Collection<Part> parts = req.getParts();

		List<Attach> attachs = new ArrayList<>();
		for (Part p : parts) {
			if (p.getContentType() == null) {
//				String name = p.getName();
//				System.out.println(name + " :: " + req.getParameter("id"));
				continue;
			} // file

			// 파일의 원본이름
			String origin = p.getSubmittedFileName();

			// 파일명 마지막 .의 위치
			int dotIdx = origin.lastIndexOf(".");

			// 확장자
			String ext = "";

			// 확장자 구하기
			if (dotIdx > -1) {// 점위치가 맨앞에 있는 경우도 있다.
				ext = origin.substring(dotIdx); // .txt
			}

			// UUID문자열 생성
			String uuid = UUID.randomUUID().toString();

			// 경로 문자열 반환
			String path = getTodayStr();

			// 경로 문자열에 대한 폴더 생성
			File targetPath = new File(ParamSolver.UPLOAD_PATH, path);
			if (!targetPath.exists()) {// 없으면 폴더 생성
				targetPath.mkdirs();
			}

			// 원본에 대한 저장
			File fs = new File(targetPath, uuid + ext);// 경로 파일명
			p.write(fs.getPath());

			// 이미지 여부 확인
			List<String> exceptImgMimes = Arrays.asList("image/x-icon", "image/webp");// x-icon과 webp에 대한 썸네일 작업을 위한
																						// List
			boolean image = p.getContentType().startsWith("image") && !exceptImgMimes.contains(p.getContentType());

			// 이미지 일때만 섬네일 이미지 제작
			if (image) {
				File out = new File(targetPath, uuid + "_t" + ext);// 경로 파일명 썸네일
				Thumbnailator.createThumbnail(fs, out, 200, 200); // 가로세로 크기
			}
			// uuid, origin, image, path
			attachs.add(new Attach(uuid, origin, image, path));
			attachs.forEach(System.out::println);
		}
	}

	private String getTodayStr() {
		return new SimpleDateFormat("yyyy/MM/dd").format(System.currentTimeMillis());
	}

}
