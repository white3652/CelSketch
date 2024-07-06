package com.celsketch.dto;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UserDTO {
    private int uIdx;
    @NotEmpty(message = "User ID cannot be empty")
    private String userId;
    private String password;
    private String confirmPw;
    private String userName;
    private String phoneNumber;
    private String email;
    private String gender;
    private LocalDate birthday;
    private MultipartFile profile;
    private int cancelOrNot;
    private String profileFileName; // 프로필 파일 이름을 저장할 필드
    private int year;
    private int month;
    private int day;

    public void setBirthdayFromFields() {
        this.birthday = LocalDate.of(this.year, this.month, this.day);
    }
}