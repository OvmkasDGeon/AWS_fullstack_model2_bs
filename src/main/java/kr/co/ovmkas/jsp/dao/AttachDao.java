package kr.co.ovmkas.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.ovmkas.jsp.domain.Attach;
import kr.co.ovmkas.jsp.util.DBConn;

public class AttachDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// 글쓰기
	public Long insert(Attach attach) {

		conn = DBConn.getConnection();
		long result = 0;
		// 처리할 sql구문
		String sql = "insert into tbl_attach values(?, ?, ?, ?, ?)";
		try {
			// 문장 생성
			// 쿼리를 먼저 날림
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setString(idx++, attach.getUuid());
			pstmt.setString(idx++, attach.getOrigin());
			pstmt.setBoolean(idx++, attach.isImage());
			pstmt.setString(idx++, attach.getPath());
			pstmt.setLong(idx++, attach.getBno());

			// 문장 처리
			pstmt.executeUpdate();
			close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 단일 조회
	public Attach selectOne(String uuid) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		Attach attach = null;
		// 처리할 sql구문
		String sql = "select * from tbl_attach where uuid=?";
		// 행을 여러개 가져올땐 while 단일일땐 if
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uuid);

			// 결과집합 반환(표형태)
			rs = pstmt.executeQuery();// 결과 집합을 rs에 보낸다 rs는 표형태

			// rs의 상태는 현재 칼럼명을 보고있는상태 next를 하면 다음 행
			// 결과집합을 자바 객체로 변환
			// LIST형태면 while문으로 돌리고 add하면서 넣어준다
			if (rs.next()) {
				int idx = 1;
				// 객체 생성 후 값 바인딩
				attach = new Attach(rs.getString(idx++), rs.getString(idx++), rs.getBoolean(idx++), rs.getString(idx++),
						rs.getLong(idx++));
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return attach;
	}

	// category별 조회
	public List<Attach> selectList(Long bno) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		List<Attach> attachs = new ArrayList<Attach>();

		// 처리할 sql구문
		String sql = "";
		sql += "select * from tbl_attach where bno=?";
		// 행을 여러개 가져올땐 while 단일일땐 if
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setLong(idx++, bno);

			// 결과집합 반환(표형태)
			rs = pstmt.executeQuery();// 결과 집합을 rs에 보낸다 rs는 표형태

			// rs의 상태는 현재 칼럼명을 보고있는상태 next를 하면 다음 행
			// 결과집합을 자바 객체로 변환
			// LIST형태면 while문으로 돌리고 add하면서 넣어준다
			while (rs.next()) {
				idx = 1;
				// 객체 생성 후 값 바인딩
				Attach attach = new Attach(rs.getString(idx++), rs.getString(idx++), rs.getBoolean(idx++),
						rs.getString(idx++), rs.getLong(idx++));
				attachs.add(attach);
			}
//			System.out.println(sql);
//			System.out.println(cri);
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return attachs;
	}

	// 삭제
	public void delete(Long bno) {
		conn = DBConn.getConnection();
		// 처리할 sql구문
		String sql = "delete from tbl_attach where bno=?";
		try {
			// 문장 생성
			// 쿼리를 먼저 날림
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bno);
			// 문장 처리
			pstmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 자원 반환
	public void close() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
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
	}
}
