package com.celsketch.mapper;

import com.celsketch.dto.UserDTO;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {
    int checkId(@Param("userId") String userId);
    UserDTO login(@Param("userId") String userId, @Param("password") String password);
    int updateUser(UserDTO user);
    int deleteUser(@Param("uIdx") int uIdx);
    UserDTO getUser(@Param("uIdx") int uIdx);
    UserDTO getUserByUserId(String userId);
    Integer getUserIdxByUserId(String userId);
    int findUserIdxByUserId(String userId);
    int updateDelOrNot(int uIdx, int delOrNot);
    int updateCancelOrNot(int uIdx, int cancelOrNot);
}