package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.ex.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

@Slf4j
@SpringBootTest
public class UserServiceTests {
    @Autowired
    IUserService userService;

    @Test
    void registerStudent(){
    try {
        User user=new User();
        user.setPhone("11111111118");
        user.setNickname("GM");
        user.setPassword("123456");
        String inviteCod="JSD2003-005803";
        userService.registerStudent(user,inviteCod);
        log.debug("执行成功 执行乘公共");

    }catch (ServiceException e){
        log.debug("注册失败");
        log.debug("出现异常{}",e.getClass());
    }
    }

    @Test
    void a(){
    UserDetails userDetails= userService.login("4576567567");

        log.debug("login,user details{}",userDetails);

    }

}
