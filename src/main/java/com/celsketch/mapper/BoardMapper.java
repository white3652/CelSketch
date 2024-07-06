package com.celsketch.mapper;

import com.celsketch.dto.BoardDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<BoardDTO> getBoardList();
    BoardDTO getBoard(int bIdx);
    BoardDTO getBoardById(int id);
    int insertBoard(BoardDTO boardDTO);
    int updateBoard(BoardDTO boardDTO);
    int deleteBoard(int bIdx);
    void incrementReadCnt(int bIdx);
    List<BoardDTO> getBoardListPaged(RowBounds rowBounds);
    int countBoards();
}