package com.edu.networkexperimentation.service;

import com.edu.networkexperimentation.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.edu.networkexperimentation.model.response.ResponseUser;

import javax.servlet.http.HttpServletRequest;


/**
 * 用户服务
 *
 * @author kfz
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param username 用户姓名
     * @param password 用户密码
     * @param checkPassword 用户加盐密码
     * @return 用户ID
     */
    long userRegister(String username, String password, String checkPassword);

    /**
     * 用户登录
     * @param id 用户ID
     * @param password 用户密码
     * @param request 连接
     * @return 去除敏感信息的用户
     */
    ResponseUser userLogin(long id, String password, HttpServletRequest request);

    /**
     * 用户退出登录
     * @param request 用户退出登录的请求
     */
    void userLogout(HttpServletRequest request);
}
