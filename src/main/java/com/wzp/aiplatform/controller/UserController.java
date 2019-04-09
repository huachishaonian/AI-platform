package com.wzp.aiplatform.controller;


import com.wzp.aiplatform.service.UserService;
import com.wzp.aiplatform.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    /**
     *  管理员或用户登录
     * @param username
     * @param password
     * @return
     */
    @GetMapping("/login")
    public Mono<ApiResult<Object>> getLoginInfo(@RequestParam String username, @RequestParam String password) {
        log.info("getLoginInfo username = {}, password = {}", username, password);
        return userService.getLoginInfo(username, password);
    }
}
