package kr.co.ovmkas.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.ovmkas.jsp.domain.Reply;
import kr.co.ovmkas.jsp.util.DBConn;

public class ReplyDao {
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;

	// 글쓰기
	public int insert(Reply reply) {
		conn = DBConn.getConnection();
		int result = 0;
		// 처리할 sql구문
		String sql = "insert into tbl_Reply(content, writer, bno) values(?, ?, ?)";
		try {
			// 문장 생성
			// 쿼리를 먼저 날림
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, reply.getContent());
			pstmt.setString(2, reply.getWriter());
			pstmt.setLong(3, reply.getBno());

			// 문장 처리
			result = pstmt.executeUpdate();
			close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Reply> selectList(Long bno) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		List<Reply> replies = new ArrayList<Reply>();

		// 처리할 sql구문
		String sql = "";
		sql = "select * from tbl_Reply where bno = ?";
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setLong(idx++, bno);

			// 결과집합 반환(표형태)
			rs = pstmt.executeQuery();// 결과 집합을 rs에 보낸다 rs는 표형태

			while (rs.next()) {
				idx = 1;
				// 객체 생성 후 값 바인딩
				Reply reply = new Reply(rs.getLong(idx++), rs.getString(idx++), rs.getDate(idx++), rs.getString(idx++),
						rs.getLong(idx++));
				replies.add(reply);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return replies;
	}
	
	public Reply selectOne(Long rno) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		Reply reply = null;
		// 처리할 sql구문
		String sql = "";
		sql = "select * from tbl_Reply where rno = ?";
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setLong(idx++, rno);
			// 결과집합 반환(표형태)
			rs = pstmt.executeQuery();// 결과 집합을 rs에 보낸다 rs는 표형태
			while (rs.next()) {
				idx = 1;
				// 객체 생성 후 값 바인딩
				reply = new Reply(
						rs.getLong(idx++), 
						rs.getString(idx++), 
						rs.getDate(idx++), 
						rs.getString(idx++),
						rs.getLong(idx++));
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reply;
	}

	public void updateWriterToNull(String id) {
		conn = DBConn.getConnection();
		// 처리할 sql구문
		String sql = "update tbl_reply set\r\n" 
				+ "	writer = NULL,\r\n" 
				+ "where writer=?";
		try {
			// 문장 생성
			// 쿼리를 먼저 날림
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);

			// 문장 처리
			pstmt.executeUpdate();

			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 삭제
	public int delete(Long rno) {
		int ret=0;
		conn = DBConn.getConnection();
		// 처리할 sql구문
		String sql = "delete from tbl_Reply where rno=?";
		try {
			// 문장 생성
			// 쿼리를 먼저 날림
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, rno);
			// 문장 처리
			ret = pstmt.executeUpdate();
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ret;
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
	public void deleteByBno(Long bno) {
		conn = DBConn.getConnection();
		
		// 처리할 sql구문
		String sql = "delete from tbl_Reply where bno=?";
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
	
	public static void main(String[] args) {
		ReplyDao dao = new ReplyDao();
//		dao.insert(new Reply("댓","id1",864L));
//		dao.delete(10L);
//		System.out.println(dao.selectList(864L));
	}


}
