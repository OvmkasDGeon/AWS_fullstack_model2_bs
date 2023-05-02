package kr.co.ovmkas.jsp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import kr.co.ovmkas.jsp.dao.AttachDao;
import kr.co.ovmkas.jsp.dao.BoardDao;
import kr.co.ovmkas.jsp.dao.ReplyDao;
import kr.co.ovmkas.jsp.domain.Attach;
import kr.co.ovmkas.jsp.domain.Board;
import kr.co.ovmkas.jsp.domain.Criteria;

public class BoardServiceImpl implements BoardService {
	private BoardDao dao = new BoardDao();
	private AttachDao attachDao = new AttachDao();
	private ReplyDao replyDao = new ReplyDao();

	@Override
	public Long register(Board board) {
		// 글 작성 후 글번호 지정
		Long bno = dao.insert(board);
		System.out.println("boardService.register :: " + bno);
		for (Attach attach : board.getAttachs()) {
			attach.setBno(bno);
			attachDao.insert(attach);
		}
		return bno;
		// int타입이기때문에 추후 수정
	}

	@Override
	public Board get(Long bno) {
		dao.increaseHitCount(bno);
		Board board = dao.selectOne(bno);
		board.setAttachs(attachDao.selectList(bno));
		return board;
	}

	@Override
	public List<Board> list(Criteria cri) {
		List<Board> list = dao.selectList(cri);
		if (cri.getCategory() == 4) {
			// map(function) map(Map<String, String> a-> board b).collect(list에 board으로)
			list = dao.selectListGallery(cri).stream().map(a -> {
				Board board = new Board();
				board.setBno(Long.valueOf(a.get("bno")));
				board.setTitle(a.get("title"));
				board.setWriter(a.get("writer"));
				String fullpath = a.get("fullpath");
				
				if (fullpath != null) {
					board.setContent(fullpath);
				}
				return board;
			}).collect(Collectors.toList());
		}
		return list;

		// return dao.selectList(cri).stream().map(board -> {
		// board.setAttachs(attachDao.selectList(board.getBno()));
		// return board;
		// }).collect(Collectors.toList());
	}

	@Override
	public void modify(Board board) {
		dao.update(board);

	}

	@Override
	public void remove(Long bno) {
		// 파일 시스템에 존재하는 파일 삭제
		// 아래랑 같은 것 한줄일때는 생략 가능한 것들이 있다.
		attachDao.selectList(bno).forEach(attach -> {
			attach.getFile().delete();
			if (attach.isImage()) {
				attach.getFile(true).delete();
			}
		});

		// attachDao.selectList(bno).forEach(attach
		// ->attach.getFile().delete());
		// 첨부 목록 삭제
		attachDao.delete(bno);
		// 댓글 삭제
		replyDao.deleteByBno(bno);
		// tbl_board 삭제
		dao.delete(bno);
	}

	@Override
	public int listCount(Criteria cri) {
		return dao.selectListCount(cri);
	}

}
