package com.celsketch.service;

import com.celsketch.dto.UserDTO;
import com.celsketch.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserMapper userMapper;

    @Override
    public void join(UserDTO userDTO) {
        userDTO.setBirthdayFromFields();
        userMapper.join(userDTO);
    }

    @Override
    public int checkId(String userId) {
        return userMapper.checkId(userId);
    }

    @Override
    public UserDTO login(String userId, String password) {
        logger.debug("Attempting login with userId: {} and password: {}", userId, password);

        UserDTO user = userMapper.login(userId, password);

        if (user != null) {
            logger.debug("Login successful for user: {}", user.getUserId());
        } else {
            logger.debug("Login failed for user: {}", userId);
        }

        return user;
    }

    @Override
    public int updateUser(UserDTO user) {
        return userMapper.updateUser(user);
    }

    @Override
    public UserDTO getUser(int uIdx) {
        return userMapper.getUser(uIdx);
    }

    @Override
    public UserDTO getUserByUserId(String userId) {
        return userMapper.getUserByUserId(userId);
    }

    @Override
    public int getUserIdxByUserId(String userId) {
        Integer userIdx = userMapper.getUserIdxByUserId(userId);
        if (userIdx == null) {
            throw new IllegalArgumentException("Invalid userId: " + userId);
        }
        return userIdx;

    }

    @Override
    public void deleteUser(String userId) {
        Integer userIdx = getUserIdxByUserId(userId);
        userMapper.updateDelOrNot(userIdx, 1);
    }

    @Override
    public int updateCancelOrNot(int uIdx, int cancelOrNot) {
        return userMapper.updateCancelOrNot(uIdx, cancelOrNot);
    }
}