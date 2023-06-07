package com.edu.networkexperimentation.controller;

import com.edu.networkexperimentation.common.BaseResponse;
import com.edu.networkexperimentation.common.ErrorCode;
import com.edu.networkexperimentation.common.ResultUtils;
import com.edu.networkexperimentation.exception.BusinessException;
import com.edu.networkexperimentation.model.domain.Grade;
import com.edu.networkexperimentation.model.domain.User;
import com.edu.networkexperimentation.model.request.RequestLoginUser;
import com.edu.networkexperimentation.model.request.RequestRegisterUser;
import com.edu.networkexperimentation.model.response.ResponseGrade;
import com.edu.networkexperimentation.model.response.ResponseUser;
import com.edu.networkexperimentation.service.GradeService;
import com.edu.networkexperimentation.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.List;

import static com.edu.networkexperimentation.contant.UserConstant.ADMIN_ROLE;
import static com.edu.networkexperimentation.contant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author yupi
 */
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @Resource
    private GradeService gradeService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody RequestRegisterUser user, HttpServletRequest request) {
        if (user == null) {
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        ResponseUser safetyUser = (ResponseUser)  request.getSession().getAttribute(USER_LOGIN_STATE);

        if (safetyUser == null || safetyUser.getUserIdentity() != ADMIN_ROLE) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }

        String username = user.getUsername();
        String password = user.getPassword();
        String checkPassword = user.getCheckPassword();
        long id = userService.userRegister(username, password, checkPassword);
        return ResultUtils.success(id);
    }

    @PostMapping("/login")
    public BaseResponse<ResponseUser> userLogin(@RequestBody RequestLoginUser loginUser, HttpServletRequest request) {
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        Long id = loginUser.getId();
        String password = loginUser.getPassword();
        ResponseUser user = userService.userLogin(id, password, request);
        return ResultUtils.success(user);
    }

    @PostMapping("/cookie/login")
    public BaseResponse<ResponseUser> userCookieLogin(HttpServletRequest request) {
        ResponseUser user = (ResponseUser) request.getSession().getAttribute(USER_LOGIN_STATE);
//        if (user == null) throw new BusinessException(ErrorCode.NULL_ERROR, "cookie登陆失败");
        return ResultUtils.success(user);
    }


    @PostMapping("/logout")
    public ResponseUser userLogout(HttpServletRequest request) {
        userService.userLogout(request);
        return null;
    }

    @GetMapping("/grade/all")
    public BaseResponse<List<ResponseGrade>> getAllGrade() {
        List<Grade> grades = gradeService.list(null);
        List<ResponseGrade> responseGrades = new ArrayList<>();
        grades.forEach(item -> {
            responseGrades.add(new ResponseGrade(item));
        });
        return ResultUtils.success(responseGrades);
    }

}
