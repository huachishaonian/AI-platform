package com.wzp.aiplatform.controller;


import com.wzp.aiplatform.service.UserService;
import com.wzp.aiplatform.utils.ApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(allowCredentials="true",origins = "http://localhost:8080")
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

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/register")
    public Mono<ApiResult<Object>> getRegisterInfo(@RequestParam String username, @RequestParam String password) {
        log.info("getRegisterInfo username = {}, password = {}", username, password);
        return userService.getRegisterInfo(username, password);
    }
}
