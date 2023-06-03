package com.edu.networkexperimentation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.contant.UserConstant;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.model.domain.User;
import com.edu.networkexperimentation.model.request.ResponseUser;
import com.edu.networkexperimentation.service.UserService;
import com.edu.networkexperimentation.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;

/**
 * 用户服务实现类
 *
 * @author kfz
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    private final String SALT = "network";

    @Override
    public long userRegister(String username, String password, String checkPassword) {
        if (StringUtils.isAnyBlank(username, password, checkPassword)) {
            throw  new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (!password.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        long result = userMapper.selectCount(queryWrapper);
        if (result >= 1) {
            throw  new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));
        User user = new User();
        user.setUsername(username);
        user.setUserPassword(encryptPassword);
        int saveResult = userMapper.insert(user);
        if (saveResult == 0) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        return user.getId();
    }

    @Override
    public ResponseUser userLogin(long id, String password, HttpServletRequest request) {

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + password).getBytes(StandardCharsets.UTF_8));

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("userPassword", encryptPassword);

        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("user login fail.");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户名或密码错误");
        }

        ResponseUser safetyUser = new ResponseUser(user);

        HttpSession session = request.getSession();
        session.setAttribute(UserConstant.USER_LOGIN_STATE, safetyUser);
//        log.info("生成的登录用户: " + ((ResponseUser)session.getAttribute(UserConstant.USER_LOGIN_STATE)).getUsername());

        return safetyUser;
    }

    @Override
    public void userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(UserConstant.USER_LOGIN_STATE);
    }
}




