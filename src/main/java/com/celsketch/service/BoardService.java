package com.celsketch.service;

import com.celsketch.dto.BoardDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BoardService {
    List<BoardDTO> getBoardList();
    BoardDTO getBoard(int bIdx);
    BoardDTO getBoardById(int id);
    int insertBoard(BoardDTO boardDTO);
    int updateBoard(BoardDTO boardDTO);
    int deleteBoard(int bIdx);
    void incrementReadCnt(int bIdx);
    Page<BoardDTO> getBoardListPaged(Pageable pageable);
}