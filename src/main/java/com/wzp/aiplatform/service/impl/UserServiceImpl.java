package com.wzp.aiplatform.service.impl;

import com.wzp.aiplatform.mapper.AdminMapper;
import com.wzp.aiplatform.mapper.UserMapper;
import com.wzp.aiplatform.model.Admin;
import com.wzp.aiplatform.model.User;
import com.wzp.aiplatform.model.po.ResUser;
import com.wzp.aiplatform.service.UserService;
import com.wzp.aiplatform.utils.ApiResult;
import com.wzp.aiplatform.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AdminMapper adminMapper;

    private Mono<Admin> selectAdminLogin(String adminname) {
        return Mono.fromSupplier(() -> {
            if (adminMapper.selectByPrimaryKey(adminname) != null){
                return adminMapper.selectByPrimaryKey(adminname);
            }
            return new Admin();
        }).publishOn(Schedulers.elastic()).doOnError(t ->
                log.error("selectAdminLogin is error!~~,adminname == {}", adminname,t))
                .onErrorReturn(new Admin());
    }
    public Mono<User> selectUserLogin(String username) {
        return Mono.fromSupplier(() -> {
            if (userMapper.selectByPrimaryKey(username) != null){
                return userMapper.selectByPrimaryKey(username);
            }
            return new User();
        }).publishOn(Schedulers.elastic()).doOnError(t ->
                log.error("selectUserLoginis error!~~,username == {}", username,t))
                .onErrorReturn(new User());
    }
    @Override
    public Mono getLoginInfo(String username, String password){
        return Mono.fromSupplier(() -> {
            Mono<Admin> adminMono = selectAdminLogin(username);
            Mono<User> userMono = selectUserLogin(username);
            return Mono.zip(adminMono, userMono).map(tuple -> {
                if (!StringUtils.isEmpty(tuple.getT1().getPassword()) && tuple.getT1().getPassword().equals(MD5Util.encryptMD5(password))){
                    ResUser resUser = new ResUser();
                    resUser.setUsername(username);
                    resUser.setIsRoot(true);
                    return ApiResult.getApiResult("admin login success", resUser);
                } else if (!StringUtils.isEmpty(tuple.getT2().getPassword()) && tuple.getT2().getPassword().equals(MD5Util.encryptMD5(password))){
                    ResUser resUser = new ResUser();
                    resUser.setUsername(username);
                    resUser.setIsRoot(false);
                    return ApiResult.getApiResult( "user login success", resUser);
                }
                return ApiResult.getApiResult(-1, "login fail");
            }).publishOn(Schedulers.elastic()).doOnError(t ->
                    log.error("getLoginInfo zip is error!~~,username == {}", username, t))
                    .onErrorReturn(ApiResult.getApiResult(-1, "login fail"));
        });
    }

    @Override
    public Mono getRegisterInfo(String username, String password) {
        return Mono.fromSupplier(() -> {
            Mono<Admin> adminMono = selectAdminLogin(username);
            Mono<User> userMono = selectUserLogin(username);
            return Mono.zip(adminMono, userMono).map(tuple -> {
                if (StringUtils.isEmpty(tuple.getT1().getPassword())
                        && StringUtils.isEmpty(tuple.getT2().getPassword())) {
                    User user = new User();
                    user.setUsername(username);
                    user.setPassword(MD5Util.encryptMD5(password));
                    if (userMapper.insert(user) > 0) {
                        return ApiResult.getApiResult(200, "user register success");
                    }
                }
                return ApiResult.getApiResult(-1, "user register fail");
            }).publishOn(Schedulers.elastic()).doOnError(t ->
                    log.error("getRegisterInfo zip is error!~~,username == {}", username, t))
                    .onErrorReturn(ApiResult.getApiResult(-1, "user register fail"));
        });
    }
}
