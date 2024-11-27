// src/main/java/com/ssafy/campcino/service/impl/BoardServiceImpl.java
package com.ssafy.campcino.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.campcino.dto.requestDto.CreateBoardRequestDto;
import com.ssafy.campcino.dto.responseDto.BoardDto;
import com.ssafy.campcino.dto.responseDto.PaginatedResponse;
import com.ssafy.campcino.mapper.BoardMapper;
import com.ssafy.campcino.model.BoardEntity;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Override
    public PaginatedResponse<BoardDto> getBoards(List<String> categories, String text, int page, int size, String sortBy, String sortOrder) {
        int offset = (page - 1) * size;
        List<BoardEntity> boards = boardMapper.getBoards(categories, text, size, offset, sortBy, sortOrder);
        int totalBoards = boardMapper.countBoards(categories, text);
        int totalPages = (int) Math.ceil((double) totalBoards / size);

        List<BoardDto> boardDtos = boards.stream().map(this::convertToDto).collect(Collectors.toList());

        PaginatedResponse<BoardDto> response = new PaginatedResponse<>();
        response.setItems(boardDtos);
        response.setCurrentPage(page);
        response.setTotalPages(totalPages);
        response.setTotalItems(totalBoards);

        return response;
    }

    @Override
    public BoardDto getBoardById(Long boardId) {
        BoardEntity board = boardMapper.getBoardById(boardId);
        if (board == null) {
        }
        // 조회수 증가
        board.setBoardView(board.getBoardView() + 1);
        boardMapper.updateBoardView(board.getBoardView(), boardId);
        return convertToDto(board);
    }

    @Override
    public BoardDto createBoard(CreateBoardRequestDto createBoardRequestDto) {
        BoardEntity board = new BoardEntity();
        board.setBoardTitle(createBoardRequestDto.getBoardTitle());
        board.setBoardContent(createBoardRequestDto.getBoardContent());
        board.setCategory(createBoardRequestDto.getCategory());
        board.setImgUrl(createBoardRequestDto.getImgUrl());
        board.setUserId(createBoardRequestDto.getUserId());
        // board.setBoardCreatedAt(LocalDateTime.now()); // 제거: DB에서 설정
        board.setBoardView(0);
        
        boardMapper.insertBoard(board); // boardId가 자동으로 설정됩니다.
        
        return convertToDto(board);
    }

    @Override
    public void updateBoardView(Long boardId) {
        // 특정 로직이 필요한 경우 구현
    }

    private BoardDto convertToDto(BoardEntity board) {
        BoardDto dto = new BoardDto();
        dto.setBoardId(board.getBoardId());
        dto.setBoardTitle(board.getBoardTitle());
        dto.setBoardContent(board.getBoardContent());
        dto.setCategory(board.getCategory());
        dto.setImgUrl(board.getImgUrl());
        dto.setUserId(board.getUserId());
        dto.setBoardCreatedAt(board.getBoardCreatedAt());
        dto.setBoardView(board.getBoardView());
        // 댓글은 별도로 처리
        return dto;
    }
    
    @Override
    public void updateBoard(CreateBoardRequestDto requestDto, Long boardId) {
        BoardEntity board = boardMapper.getBoardById(boardId);
        if (board == null) {
            throw new RuntimeException("존재하지 않는 게시글입니다.");
        }
        board.setBoardTitle(requestDto.getBoardTitle());
        board.setBoardContent(requestDto.getBoardContent());
        board.setCategory(requestDto.getCategory());
        board.setImgUrl(requestDto.getImgUrl());
        boardMapper.updateBoard(board);
    }
    
    @Override
    public void deleteBoard(Long boardId) {
        BoardEntity board = boardMapper.getBoardById(boardId);
        if (board == null) {
            throw new RuntimeException("존재하지 않는 게시글입니다.");
        }
        boardMapper.deleteBoard(boardId);
    }


}
