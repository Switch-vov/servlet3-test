package com.switchvov.servlet3test.controller;

import com.switchvov.servlet3test.common.ResponseResult;
import com.switchvov.servlet3test.common.thread.CoreAsyncContext;
import com.switchvov.servlet3test.entity.User;
import com.switchvov.servlet3test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Switch
 * @Date 2018/1/21
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private CoreAsyncContext coreAsyncContext;

    @GetMapping("/{id}")
    public ResponseResult<?> getUser(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return ResponseResult.successWithData(user);
    }

    @GetMapping("/async/{id}")
    public void getUserByAsync(HttpServletRequest request, @PathVariable("id") Long id) {
        coreAsyncContext.submitFuture(request, () -> userService.getById(id));
    }
}
