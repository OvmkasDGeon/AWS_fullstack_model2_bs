package kr.co.ovmkas.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import kr.co.ovmkas.jsp.domain.Member;
import kr.co.ovmkas.jsp.util.DBConn;

public class MemberDao {
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet rs;
	

	// 회원가입
	public int insert(Member vo) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "insert into tbl_member(id, pw, name, email, addr, addrDetail) value(?, ?, ?, ?, ?, ?)";
		try {
			// 문장 생성
			stmt = conn.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, vo.getId());
			stmt.setString(idx++, vo.getPw());
			stmt.setString(idx++, vo.getName());
			stmt.setString(idx++, vo.getEmail());
			stmt.setString(idx++, vo.getAddr());
			stmt.setString(idx++, vo.getAddrDetail());

			// 문장 처리
			result = stmt.executeUpdate();
			close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updatePw(Member vo) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "update tbl_member set pw = ? where id = ?";
		try {
			// 문장 생성
			stmt = conn.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, vo.getPw());
			stmt.setString(idx++, vo.getId());

			// 문장 처리
			result = stmt.executeUpdate();
			close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public int update(Member vo) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "update tbl_member set name =?, email =?, addr=?, addrDetail=? where id = ?";
		try {
			// 문장 생성
			stmt = conn.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, vo.getName());
			stmt.setString(idx++, vo.getEmail());
			stmt.setString(idx++, vo.getAddr());
			stmt.setString(idx++, vo.getAddrDetail());
			stmt.setString(idx++, vo.getId());

			// 문장 처리
			result = stmt.executeUpdate();
			close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public int delete(Member vo) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "delete tbl_member where id = ?";
		try {
			// 문장 생성
			stmt = conn.prepareStatement(sql);
			int idx = 1;
			stmt.setString(idx++, vo.getId());

			// 문장 처리
			result = stmt.executeUpdate();
			close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Member selectOne(String id) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		Member vo = null;
		// 처리할 sql구문
		String sql = "select * from tbl_member where id = ?";
		
		// 행을 여러개 가져올땐 while 단일일땐 if
		try {
			// 문장생성
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, id);
			// 결과집합 반환(표형태)
			rs = stmt.executeQuery();// 결과 집합을 rs에 보낸다 rs는 표형태

			// rs의 상태는 현재 칼럼명을 보고있는상태 next를 하면 다음 행
			// 결과집합을 자바 객체로 변환
			// LIST형태면 while문으로 돌리고 add하면서 넣어준다
			if (rs.next()) {
				int idx = 1;
				// 객체 생성 후 값 바인딩
				vo = new Member(// rs.getString(1)숫자를 쓰면 첫번째 인덱스를 가져오고 rs.getString("id"); 이렇게 하면 id칼럼을 가져온다
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getDate(idx++),
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++)
						);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return vo;
	}
	
	public List<Member> selectList() {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		List<Member> vo = new ArrayList<>();
		// 처리할 sql구문
		String sql = "select * from tbl_member";
		
		// 행을 여러개 가져올땐 while 단일일땐 if
		try {
			// 문장생성
			stmt = conn.prepareStatement(sql);
			// 결과집합 반환(표형태)
			rs = stmt.executeQuery();// 결과 집합을 rs에 보낸다 rs는 표형태

			// rs의 상태는 현재 칼럼명을 보고있는상태 next를 하면 다음 행
			// 결과집합을 자바 객체로 변환
			// LIST형태면 while문으로 돌리고 add하면서 넣어준다
			while (rs.next()) {
				int idx = 1;
				// 객체 생성 후 값 바인딩
				Member member = new Member(// rs.getString(1)숫자를 쓰면 첫번째 인덱스를 가져오고 rs.getString("id"); 이렇게 하면 id칼럼을 가져온다
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getDate(idx++),
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++)
						);
				vo.add(member);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return vo;
	}

	// 자원 반환
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
			}
		}
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
	}

	public static void main(String[] args) {
		MemberDao dao = new MemberDao();
//		dao.selectList().forEach(member-> {
//			member.setPw(BCrypt.hashpw("a1234", BCrypt.gensalt()));
//			dao.updatePw(member);
//		});
	}
}
