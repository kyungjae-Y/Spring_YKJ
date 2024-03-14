package kr.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.board.entity.Board;
import kr.board.mapper.BoardMapper;

@RequestMapping("/boards")
@RestController
public class BoardController {
	@Autowired
	BoardMapper boardMapper;

	@GetMapping("")
	public List<Board> boardList() {
		List<Board> list = boardMapper.getLists();
		return list;
	}

	@PostMapping("")
	public String addBoard(@RequestBody Board board) {
		String msg = board.toString();
		int result = boardMapper.boardInsert(board);
		return (result == 1 ? "게시글 추가 완료" : "게시글 추가 실패") + msg;
	}

	@GetMapping("/{idx}")
	public Board getOneBoard(@PathVariable int idx) {
		Board board = boardMapper.boardContent(idx);
		return board;
	}

	@DeleteMapping("/{idx}")
	public String deleteOneBoard(@PathVariable int idx) {
		int result = boardMapper.boardDelete(idx);
		return result == 1 ? "게시글 삭제 완료" : "게시글 삭제 실패";
	}

	@PutMapping("/{idx}")
	public String updateBoard(@RequestBody Board board, @PathVariable int idx) {
		board.setIdx(idx);
		int result = boardMapper.boardUpdate(board);
		return result == 1 ? "게시글 수정 완료" : "게시글 수정 실패";
	}
}
