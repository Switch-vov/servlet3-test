package com.switchvov.servlet3test.service.impl;

import com.switchvov.servlet3test.entity.User;
import com.switchvov.servlet3test.repository.UserRepository;
import com.switchvov.servlet3test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author Switch
 * @Date 2018/1/21
 */
@Service
public class UserServiceImpl implements UserService{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getById(Long id) {
        return userRepository.findOne(id);
    }
}
