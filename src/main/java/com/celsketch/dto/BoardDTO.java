package com.celsketch.dto;

import lombok.Data;
import java.util.Date;

@Data
public class BoardDTO {
    private int bIdx;
    private String userId;
    private String writer;
    private String title;
    private String content;
    private String originFileName;
    private String saveFileName;
    private String tag;
    private int age;
    private int ai;
    private int pbc;
    private int org;
    private int readCnt;
    private Date postDate;
    private Date updateDate;
    private int delOrNot;
    private String filePath;
}