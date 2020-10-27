package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.co.R;
import cn.tedu.straw.portal.co.TeacherVO;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ex.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-15
 */
@Slf4j
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private IUserService userService;

    // http://localhost:8080/portal/user/student/register?inviteCode=JSD2003-111111&nickname=Hello&phone=13800138002&password=1234
    @RequestMapping("/student/register")
    public R studentRegister(String inviteCode, @Validated User user, BindingResult bindingResult) {
       if(inviteCode==null||inviteCode.length()<4){

       }

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult
                    .getFieldError().getDefaultMessage();
            log.debug("validation has error : {}", errorMessage);
            throw new ParameterValidationException(errorMessage);
        }


        userService.registerStudent(user, inviteCode);
        return R.ok();
    }
    @RequestMapping("/teacher/list")
    public R<List<TeacherVO>> teacherList(){
          List<TeacherVO> teachers= userService.findteachers();
          return R.ok(teachers);
    }

}