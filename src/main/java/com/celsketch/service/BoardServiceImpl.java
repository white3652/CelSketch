package com.celsketch.service;

import com.celsketch.dto.BoardDTO;
import com.celsketch.mapper.BoardMapper;
import com.celsketch.util.PageImpl;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    @Override
    public List<BoardDTO> getBoardList() {
        return boardMapper.getBoardList();
    }

    @Override
    public BoardDTO getBoard(int bIdx) {
        return boardMapper.getBoard(bIdx);
    }

    @Override
    public BoardDTO getBoardById(int id) {
        return boardMapper.getBoardById(id);
    }

    @Override
    public int insertBoard(BoardDTO boardDTO) {
        return boardMapper.insertBoard(boardDTO);
    }

    @Override
    public int updateBoard(BoardDTO boardDTO) {
        return boardMapper.updateBoard(boardDTO);
    }

    @Override
    public int deleteBoard(int bIdx) {
        return boardMapper.deleteBoard(bIdx);
    }

    @Override
    public void incrementReadCnt(int bIdx) {
        boardMapper.incrementReadCnt(bIdx);
    }

    @Override
    public Page<BoardDTO> getBoardListPaged(Pageable pageable) {
        int offset = (int) pageable.getOffset();
        int limit = pageable.getPageSize();
        RowBounds rowBounds = new RowBounds(offset, limit);

        List<BoardDTO> boardList = boardMapper.getBoardListPaged(rowBounds);
        int total = boardMapper.countBoards();

        return new PageImpl<>(boardList, pageable, total);
    }
}