package com.celsketch.controller;

import com.celsketch.dto.UserDTO;
import com.celsketch.service.UserService;
import com.celsketch.util.FileUploadUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final FileUploadUtil fileUploadUtil;

    @GetMapping("/join")
    public String showJoinForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user/join";
    }

    @PostMapping("/join")
    public String joinUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user/join";
        }

        // 디버깅 메시지 추가
        System.out.println("Received userId: " + userDTO.getUserId());

        // userId가 빈 값이 아니도록 검사
        if (userDTO.getUserId() == null || userDTO.getUserId().isEmpty()) {
            bindingResult.rejectValue("userId", "error.user", "User ID cannot be empty");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "user/join";
        }

        userDTO.setBirthdayFromFields();
        userService.join(userDTO);
        return "redirect:/";
    }

    @GetMapping("/update")
    public String showUpdateForm(HttpSession session, Model model) {
        UserDTO user = (UserDTO) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "user/update";
    }

    @PostMapping(value = "/update", consumes = {"multipart/form-data"})
    public String updateUser(@RequestParam("profile") MultipartFile profileFile, @Valid @ModelAttribute("user") UserDTO user, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "user/update";
        }

        if (profileFile != null && !profileFile.isEmpty()) {
            try {
                String newFileName = fileUploadUtil.uploadFile(profileFile);
                user.setProfileFileName(newFileName); // 파일 이름을 저장할 필드에 설정
            } catch (Exception e) {
                logger.error("File upload error", e);
                model.addAttribute("message", "파일 업로드 중 오류가 발생했습니다.");
                return "error";
            }
        }

        int updateResult = userService.updateUser(user);
        if (updateResult == 1) {
            session.setAttribute("user", user);
            return "redirect:/";
        } else {
            result.reject("updateFailed", "시스템 문제로 회원정보가 정상적으로 변경되지 못했습니다. 다음에 다시 변경해 주세요.");
            return "user/update";
        }
    }

    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            return "user/login";
        }

        UserDTO authenticatedUser = userService.login(userDTO.getUserId(), userDTO.getPassword());
        if (authenticatedUser != null) {
            session.setAttribute("user", authenticatedUser);
            return "redirect:/board";
        } else {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "user/login";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteUser(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        Map<String, Object> response = new HashMap<>();

        try {
            int userIdx = userService.getUserIdxByUserId(userId);
            int result = userService.updateCancelOrNot(userIdx, 1);

            if (result > 0) {
                response.put("success", true);
                response.put("message", "회원탈퇴가 완료되었습니다.");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "User deletion failed: Invalid userId");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "User deletion failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/check-id")
    @ResponseBody
    public ResponseEntity<Boolean> checkId(@RequestParam String userId) {
        int count = userService.checkId(userId);
        return ResponseEntity.ok(count == 0);
    }
}