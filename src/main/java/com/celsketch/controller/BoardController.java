package com.celsketch.controller;

import com.celsketch.dto.BoardDTO;
import com.celsketch.dto.UserDTO;
import com.celsketch.service.BoardService;
import com.celsketch.util.FileUploadUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
    private final BoardService boardService;
    private final FileUploadUtil fileUploadUtil;

    @GetMapping
    public String board(Model model, HttpSession session, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "7") int size) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null) {
            logger.debug("User not found in session, redirecting to login page.");
            return "redirect:/user/login";
        }
        model.addAttribute("user", userDTO);

        Pageable pageable = PageRequest.of(page, size);
        Page<BoardDTO> boardPage = boardService.getBoardListPaged(pageable);
        model.addAttribute("boardList", boardPage.getContent());
        model.addAttribute("page", boardPage);

        return "board/logged-in-board";
    }

    @GetMapping("/write")
    public String getBoardWriterPage(HttpSession session, Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/login";
        }
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setUserId(currentUser.getUserId());
        boardDTO.setWriter(currentUser.getUserId());
        model.addAttribute("user", currentUser);
        model.addAttribute("boardDTO", boardDTO);
        return "board/board-write";
    }
    @PostMapping("/write")
    public String createBoard(@Valid @ModelAttribute("boardDTO") BoardDTO boardDTO,
                              BindingResult bindingResult,
                              @RequestParam("uploadFile") MultipartFile uploadFile,
                              HttpSession session, Model model) {

        if (bindingResult.hasErrors()) {
            return "board/board-write";
        }

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null) {
            return "redirect:/user/login";
        }

        boardDTO.setUserId(userDTO.getUserId());
        boardDTO.setWriter(userDTO.getUserId());

        if (!uploadFile.isEmpty()) {
            try {
                String newFileName = fileUploadUtil.uploadFile(uploadFile);
                boardDTO.setOriginFileName(uploadFile.getOriginalFilename());
                boardDTO.setSaveFileName(newFileName);
            } catch (Exception e) {
                logger.error("File upload error", e);
                model.addAttribute("message", "파일 업로드 중 오류가 발생했습니다.");
                return "error";
            }
        }

        int result = boardService.insertBoard(boardDTO);
        if (result == 1) {
            return "redirect:/board";
        } else {
            model.addAttribute("message", "글 등록에 실패했습니다.");
            return "error";
        }
    }

    @GetMapping("/view/{id}")
    public String getBoardDetail(@PathVariable("id") int id, Model model, HttpSession session) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);

        // 조회수 증가
        boardService.incrementReadCnt(id);

        BoardDTO board = boardService.getBoardById(id);
        model.addAttribute("board", board);
        return "board/board-view";
    }



    @GetMapping("/update/{bIdx}")
    public String getBoardUpdatePage(@PathVariable("bIdx") int bIdx, HttpSession session, Model model) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null) {
            return "redirect:/user/login";
        }

        BoardDTO board = boardService.getBoard(bIdx);
        if (board == null) {
            model.addAttribute("message", "해당 게시글이 존재하지 않습니다.");
            return "error";
        }

        if (!board.getUserId().equals(userDTO.getUserId())) {
            model.addAttribute("message", "수정할 권한이 없습니다.");
            return "error";
        }

        model.addAttribute("user", userDTO);
        model.addAttribute("boardDTO", board);
        return "board/board-update";
    }

    @PostMapping("/update/{bIdx}")
    public String updateBoard(@PathVariable("bIdx") int bIdx,
                              @Valid @ModelAttribute("boardDTO") BoardDTO boardDTO,
                              BindingResult bindingResult,
                              @RequestParam("uploadFile") MultipartFile uploadFile,
                              HttpSession session, Model model) {

        if (bindingResult.hasErrors()) {
            return "board/board-update";
        }

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null) {
            return "redirect:/user/login";
        }

        if (!boardDTO.getUserId().equals(userDTO.getUserId())) {
            model.addAttribute("message", "수정할 권한이 없습니다.");
            return "error";
        }

        if (!uploadFile.isEmpty()) {
            try {
                String newFileName = fileUploadUtil.uploadFile(uploadFile);
                boardDTO.setOriginFileName(uploadFile.getOriginalFilename());
                boardDTO.setSaveFileName(newFileName);
            } catch (Exception e) {
                logger.error("File upload error", e);
                model.addAttribute("message", "파일 업로드 중 오류가 발생했습니다.");
                return "error";
            }
        }

        int result = boardService.updateBoard(boardDTO);
        if (result == 1) {
            return "redirect:/board";
        } else {
            model.addAttribute("message", "글 수정에 실패했습니다.");
            return "error";
        }
    }


    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteBoard(@PathVariable("id") int id, HttpSession session) {
        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
        }

        BoardDTO board = boardService.getBoard(id);
        if (board == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Board not found");
        }

        if (!board.getUserId().equals(userDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
        }

        boardService.deleteBoard(id);
        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }
}