package kr.co.ovmkas.jsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import kr.co.ovmkas.jsp.domain.Board;
import kr.co.ovmkas.jsp.domain.Criteria;
import kr.co.ovmkas.jsp.util.DBConn;

public class BoardDao {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	// 글쓰기
	public Long insert(Board board) {

		conn = DBConn.getConnection();
		long result = 0;
		// 처리할 sql구문
		String sql = "insert into tbl_board(title, content, writer, category) values(?, ?, ?, ?)";
		try {
			// 문장 생성
			// 쿼리를 먼저 날림
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setString(3, board.getWriter());
			pstmt.setInt(4, board.getCategory());
			// 문장 처리
			pstmt.executeUpdate();
			close();
			//작성한 게시글의 글번호 조회
			sql = "select max(bno) from tbl_board";
			conn = DBConn.getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();//커서이동 행이동 (boolean형태)
			result = rs.getLong(1); //1번 칼럼을 가져와서 result에 바인딩
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	// 단일 조회
	public Board selectOne(Long bno) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		Board board = null;
		// 처리할 sql구문
		String sql = "select tb.*, (select count(*) from tbl_reply tr where tr.bno  = tb.bno) replyCnt\r\n"
				+ "from tbl_board tb where bno = ?";
		// 행을 여러개 가져올땐 while 단일일땐 if
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, bno);

			// 결과집합 반환(표형태)
			rs = pstmt.executeQuery();// 결과 집합을 rs에 보낸다 rs는 표형태

			// rs의 상태는 현재 칼럼명을 보고있는상태 next를 하면 다음 행
			// 결과집합을 자바 객체로 변환
			// LIST형태면 while문으로 돌리고 add하면서 넣어준다
			if (rs.next()) {
				int idx = 1;
				// 객체 생성 후 값 바인딩
				board = new Board(
						rs.getLong(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++), 
						rs.getString(idx++),
						rs.getDate(idx++), 
						rs.getString(idx++), 
						rs.getInt(idx++),
						rs.getInt(idx++),
						null,
						rs.getInt(idx++));
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return board;
	}

	// category별 조회
	public List<Board> selectList(Criteria cri) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		List<Board> boards = new ArrayList<Board>();
		
		// 처리할 sql구문
		String sql = "";
		sql += "select tb.*, (select count(*) from tbl_reply tr where tr.bno  = tb.bno) replyCnt\r\n"
				+ "from tbl_board tb "
				+ "where category = ?";
		sql += getSearchSqlBy(cri);
		sql += " order by bno desc limit ? offset ?";
		// 행을 여러개 가져올땐 while 단일일땐 if
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setInt(idx++, cri.getCategory());
			pstmt.setInt(idx++, cri.getAmount());
			pstmt.setInt(idx++, (cri.getPageNum() - 1) * cri.getAmount());

			// 결과집합 반환(표형태)
			rs = pstmt.executeQuery();// 결과 집합을 rs에 보낸다 rs는 표형태

			// rs의 상태는 현재 칼럼명을 보고있는상태 next를 하면 다음 행
			// 결과집합을 자바 객체로 변환
			// LIST형태면 while문으로 돌리고 add하면서 넣어준다
			while (rs.next()) {
				idx = 1;
				// 객체 생성 후 값 바인딩
				Board board = new Board(
						rs.getLong(idx++),
						rs.getString(idx++),
						rs.getString(idx++),
						rs.getString(idx++),
						rs.getDate(idx++),
						rs.getString(idx++),
						rs.getInt(idx++),
						rs.getInt(idx++),
						null,
						rs.getInt(idx++));
				boards.add(board);
			}
//			System.out.println(sql);
//			System.out.println(cri);
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return boards;
	}
	
	public List<Map<String, String>> selectListGallery(Criteria cri) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		List<Map<String, String>> boards = new ArrayList<>();
		
		// 처리할 sql구문
		String sql = "";
		sql += "select bno, title, writer, "
				+ " (select concat(path,'/',uuid, '.', substring_index(origin,'.',-1)) from tbl_attach ta where ta.bno = tb.bno and image= 1 limit 1) fullpath "
				+ " from tbl_board tb "
				+ " where category = ?";
		sql += getSearchSqlBy(cri);
		sql += " order by bno desc limit ? offset ?";
		// 행을 여러개 가져올땐 while 단일일땐 if
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			int idx = 1;
			pstmt.setInt(idx++, cri.getCategory());
			pstmt.setInt(idx++, cri.getAmount());
			pstmt.setInt(idx++, (cri.getPageNum() - 1) * cri.getAmount());

			// 결과집합 반환(표형태)
			rs = pstmt.executeQuery();// 결과 집합을 rs에 보낸다 rs는 표형태
			ResultSetMetaData rsmd = rs.getMetaData();
			while (rs.next()) {
				idx = 1;
				// 객체 생성 후 값 바인딩
				Map<String, String> map = new HashMap<>();
				for(int i=0; i<rsmd.getColumnCount(); i++){
					map.put(rsmd.getColumnName(idx), rs.getString(idx++));
				}
				boards.add(map);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return boards;
	}


	public int selectListCount(Criteria cri) {
		conn = DBConn.getConnection();
		// 반환 예정 객체
		int count = 0;
		// 처리할 sql구문
		String sql = "select count(*) from tbl_board where category = ?";
		sql += getSearchSqlBy(cri);
		
		// 행을 여러개 가져올땐 while 단일일땐 if
		try {
			// 문장생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, cri.getCategory());

			// 결과집합 반환(표형태)
			rs = pstmt.executeQuery();// 결과 집합을 rs에 보낸다 rs는 표형태

			// rs의 상태는 현재 칼럼명을 보고있는상태 next를 하면 다음 행
			// 결과집합을 자바 객체로 변환
			// LIST형태면 while문으로 돌리고 add하면서 넣어준다
			while (rs.next()) {
				// 객체 생성 후 값 바인딩
				count = rs.getInt(1);
			}
			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 결과 반환
		return count;
	}

	// 수정
	public void update(Board board) {
		conn = DBConn.getConnection();
		// 처리할 sql구문
		String sql = "update tbl_board set\r\n" + "	title = ?,\r\n" + "	content = ?,\r\n" + "	updatedate = now() \r\n"
				+ "where bno=?";
		try {
			// 문장 생성
			// 쿼리를 먼저 날림
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, board.getTitle());
			pstmt.setString(2, board.getContent());
			pstmt.setLong(3, board.getBno());

			// 문장 처리
			pstmt.executeUpdate();

			close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//조회수 
	public void increaseHitCount(Long bno) {
		conn = DBConn.getConnection();
		// 처리할 sql구문
		String sql = "update tbl_board set hitcount = hitcount+1 where bno=?";
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

	// 삭제
	public void delete(Long bno) {
		conn = DBConn.getConnection();
		// 처리할 sql구문
		String sql = "delete from tbl_board where bno=?";
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

	public void updateWriterToNull(String id) {
		conn = DBConn.getConnection();
		// 처리할 sql구문
		String sql = "update tbl_board set\r\n" 
				+ "	writer = NULL\r\n" 
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

	private String getSearchSqlBy(Criteria cri) {
		String sql = "";
		if (cri.getType() != null && cri.getKeyword() != null && String.join("", cri.getType()).length()>0) {
			
//			sql += " and title Like concat_ws('%',?,'%')";
			sql += " and ( ";
			List<String> list = Arrays.asList(cri.getType());
			//T C W
			List<String> typeList = list.stream().map(s->" " + s + " like '%" +  cri.getKeyword() + "%' ").collect(Collectors.toList());
			sql += String.join(" or ", typeList);
//			sql += " title like '%"+ cri.getKeyword() + "%' ";
//			sql += " or";
//			sql += " content like '%"+ cri.getKeyword() + "%' ";
//			sql += " or";
//			sql += " writer like '%"+ cri.getKeyword() + "%' ";
					
			sql += ")";
		}
		return sql;
	}
	public static void main(String[] args) {
		Criteria criteria = new Criteria(1, 12);
		criteria.setCategory(4);
		
		List<?> list = new BoardDao().selectListGallery(criteria);
		System.out.println(list);
		System.out.println(list.size());
		new BoardDao().selectListGallery(criteria).forEach(System.out::println);
		// 글쓰기
//		dao.insert(new Board("java dao에서 테스트", "내용", "id1"));
		// 단일조회
//		System.out.println(dao.selectOne(1L));
		// 목록 조회
//		new BoardDao().selectList(1).forEach(System.out::println);

		// 수정
//		Board board = dao.selectOne(3L);
//		System.out.println(board);
//		board.setTitle("java에서 수정한 제목");
//		board.setContent("java에서 수정한 내용");
//		dao.update(board);
//		System.out.println(dao.selectOne(3L));

		// 삭제
//		Long no = 4L;
//		Board board = dao.selectOne(no);
//		System.out.println(board);
//		dao.delete(no);
//		board = dao.selectOne(no);
//		System.out.println(board);

//		System.out.println(dao.selectListCount(1));

//		String str = "12345";
//		String[] result = str.split("");
//		System.out.println(result.length);
//		String result2 = String.join(" or ", result);
//		System.out.println(result2);
		
//		new BoardDao().updateWriterToNull("idnum7");
		
	}

}
