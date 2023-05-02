package test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileTest {
	public static void main(String[] args) throws IOException {
//		System.out.println(File.separator);	   // \ 출력
//		System.out.println(File.pathSeparator);// ; 출력
//		File file = new File("c:"+File.separator);
//		System.out.println(file);
//		System.out.println(file.isDirectory());
//		System.out.println(file.isFile());

//		File권한(RWX (READWRITEEXECUTION) 2진수 표현)
//		101 : 읽고      실행        5
//		111 : 읽고 쓰고 실행		7
//		100 : 읽기           전용	4
	
//		Hidden
		
		
		
		/*실행 구문1*/
//		File file = new File("abcd"); //파일 인스턴스 생성
//		System.out.println(file.exists());//파일존재여부(현재 프로젝트 내에)
//		file.createNewFile(); //파일생성 (exception필요) 있는 파일이면 만들지 않는다
//		
//		File file2 = new File("abcde");
//		System.out.println(file2.exists());
//		System.out.println(file2.mkdir());//폴더 생성
//		
//		//일단위 폴더 c:\\upload\\2023\\03\\14
//		File file3 = new File("c:/upload", new SimpleDateFormat("yyyy/MM/dd").format(new Date()));
//		//File file3 = new File("c:/upload/2023/03/14");
//		System.out.println(file3.mkdirs());
//		file3 = new File(file3, "test.txt");//file4를 만들고 해도된다
//		file3.createNewFile();
		/*실행 구문1*/
		
		/*실행 구문2*/
		File file = new File("c:/users/SSOYOUNG");
		File[] files = file.listFiles();
		for(File f :  files) {
//			System.out.println(f);//파일명
			System.out.print(new SimpleDateFormat("yyyy-MM-dd a HH:mm:ss").format(f.lastModified())+ " ");
			if(f.isDirectory()) {//디렉토리 인지(폴더)
				System.out.print("     <DIR>     ");
			}
			if(f.isFile()) { //파일 크기
				System.out.print(f.length() + "bytes     ");
			}
			System.out.println("           " +f.getName());
		}
		/*실행 구문2*/
		
		
//		String str = "a.b.c.txt";
		String str = "abcde";
		System.out.println(str.substring(str.lastIndexOf(".")));
		System.out.println(new File("c:/upload", "abcd.txt").getPath());
		
		
	}
}
