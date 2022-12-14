package com.cos.blog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.Board;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	@Autowired
	private ReplyRepository replyRepository;

	/**
	 * 글저장
	 * @param board
	 * @param user
	 */
	@Transactional
	public void boardSave(Board board, User user) {
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	/**
	 * 글목록
	 * @param pageable
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	/**
	 * 글상세보기
	 * @param id
	 * @return
	 */
	@Transactional(readOnly = true)
	public Board boardInfo(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("없는 페이지입니다.");
				});
	}
	
	@Transactional
	public void boardDelete(int id) {
		boardRepository.deleteById(id);
	}
	
	@Transactional
	public void boardUpdate(int id, Board reqBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("수정할 수 없습니다.");
				});
		board.setTitle(reqBoard.getTitle());
		board.setContent(reqBoard.getContent());
	}
	
	@Transactional
	public void replySave(User user, int boardId, Reply requestReply) {
		
		Board board = boardRepository.findById(boardId)
				.orElseThrow(()->{
					return new IllegalArgumentException("게시글 ID를 찾을 수 없습니다.");
				});
		
		requestReply.setUser(user);
		requestReply.setBoard(board);
		
		replyRepository.save(requestReply);
	}
	
	@Transactional
	public void replyDelete(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
}

