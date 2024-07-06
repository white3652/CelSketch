package com.celsketch.dao;

import com.celsketch.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDAO {

    int join(UserDTO user);

    int checkId(@Param("userId") String userId);

    UserDTO login(@Param("userId") String userId, @Param("password") String password);

    int updateUser(UserDTO user);

    int deleteUser(@Param("uIdx") int uIdx);

    UserDTO getUser(@Param("uIdx") int uIdx);
}