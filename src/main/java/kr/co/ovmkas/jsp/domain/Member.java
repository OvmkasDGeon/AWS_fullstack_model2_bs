package kr.co.ovmkas.jsp.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data //lombok
//@Getter,@Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 따로따로 해줄 수 있다 필요한 것만
//@ToSTring() 안에 파라미터 값을 call로 써준다면 조상의 것도 할수 있따
@NoArgsConstructor// 기본생성자
@AllArgsConstructor//모든 필드 생성자
public class Member {
	
	//outline과 다른 구조를 만들고 싶으면 오버라이드 하면 된다
	private String id;
	private String pw;
	private String name;
	private Date regdate;
	
	private String email;
	private String addr;
	private String addrDetail;
	
	
}
