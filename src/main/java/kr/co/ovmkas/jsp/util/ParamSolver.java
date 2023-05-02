package kr.co.ovmkas.jsp.util;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import kr.co.ovmkas.jsp.domain.Attach;
import kr.co.ovmkas.jsp.domain.Board;
import kr.co.ovmkas.jsp.domain.Member;
import net.coobird.thumbnailator.Thumbnailator;

/*공부해볼것*/
//중요한 파일 여러 곳에서 사용 할 것으로 예상됨

public class ParamSolver {
	public static final String UPLOAD_PATH = "c:/upload";

	// 로그인 여부
	public static boolean isLogin(HttpServletRequest req) {
		return req.getSession().getAttribute("member") != null;
	}

	// 작성자와 세션의 ID와 동일한지
	// 세션에 대한 ID값 가져오는것 확인
	public static boolean isMine(HttpServletRequest req, String writer) {
		return isLogin(req) && ((Member) req.getSession().getAttribute("member")).getId().equals(writer);
	}

	// 대량의 파라미터를 처리할 때 VO에 바인딩 하기 위해서
	// reflection

	public static <T> T getParams(HttpServletRequest req, Class<T> clazz) {
		T t = null;
		try {
			t = clazz.getDeclaredConstructor().newInstance();
			// 선언 필드에 대한 타입 및 이름 체크
			Field[] fields = clazz.getDeclaredFields();// 선언한 필드를 가져온다
			for (Field f : fields) {
//			System.out.println(f.getType() + " : " +  f.getName());
				String fieldName = f.getName();
				String setterName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				Method[] methods = clazz.getDeclaredMethods();
				for (Method m : methods) {
					if (setterName.equals(m.getName())) {
						if (req.getParameter(fieldName) == null) {
							continue;
						}
						if (f.getType() == int.class || f.getType() == Integer.class) {
							m.invoke(t, Integer.parseInt(req.getParameter(fieldName)));
						}
						if (f.getType() == String.class) {
							m.invoke(t, req.getParameter(fieldName));
						}
						if (f.getType() == String[].class) {
							m.invoke(t, (Object) req.getParameterValues(fieldName));
						}
						if (f.getType() == Long.class || f.getType() == long.class) {
							m.invoke(t, Long.valueOf(req.getParameter(fieldName)));
						}
					}
				}
			}
			// 이미지
			if (req.getContentType()==null || !req.getContentType().startsWith("multipart")) {
				return t;
			}
			Collection<Part> parts = req.getParts();

			List<Attach> attachs = new ArrayList<>();
			for (Part p : parts) {
				if (p.getContentType() == null) {
//					String name = p.getName();
//					System.out.println(name + " :: " + req.getParameter("id"));
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
				//!exceptImgMimes.contains(p.getContentType()) 이걸 안해주면 아래 if문을 try~catch문을 써야 하는데 unsupportedFormatException ignore 처리를 해주고 그안데 image=false 처리를 해줘야 한다

				// 이미지 일때만 섬네일 이미지 제작
				if (image) {
					File out = new File(targetPath, uuid + "_t" + ext);// 경로 파일명 썸네일
					Thumbnailator.createThumbnail(fs, out, 400, 400); // 가로세로 크기
				}
				// uuid, origin, image, path
				attachs.add(new Attach(uuid, origin, image, path));
//				attachs.forEach(System.out::println);
			}
			if(clazz == Board.class) {
				((Board)t).setAttachs(attachs);//t를 board로 캐스팅하고 board에 있는 Attachs를 불러온 것
				
			}

//			System.out.println(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 폴더 생성에 대한 메소드
	 * 
	 * @return
	 */
	private static String getTodayStr() {
		return new SimpleDateFormat("yyyy/MM/dd").format(System.currentTimeMillis());
	}

	public static void main(String[] args) {
//		getParams(null, Criteria.class);
//		System.out.println();
	}
}
