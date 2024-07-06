package com.celsketch.service;

import com.celsketch.dto.UserDTO;

public interface UserService {
    void join(UserDTO user);
    int checkId(String userId);
    UserDTO login(String userId, String password);
    int updateUser(UserDTO user);
    public void deleteUser(String userId);
    UserDTO getUser(int uIdx);
    public UserDTO getUserByUserId(String userId);
    public int getUserIdxByUserId(String userId);
    int updateCancelOrNot(int uIdx, int cancelOrNot);
}