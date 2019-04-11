package com.wzp.aiplatform.service;

import com.wzp.aiplatform.model.User;
import com.wzp.aiplatform.utils.ApiResult;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<User> selectUserLogin(String username);

    /**
     * 管理员或用户登录
     * @param username
     * @param password
     * @return
     */
    Mono<ApiResult<Object>> getLoginInfo(String username, String password);

    /**
     * 用户注册
     * @param username
     * @param password
     * @return
     */
    Mono<ApiResult<Object>> getRegisterInfo(String username, String password);

}