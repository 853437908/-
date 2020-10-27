package cn.tedu.straw.portal;

import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StrawPortalApplicationTests {

    @Autowired
    UserMapper userMapper;
    @Test
    void contextLoads() {
        User user=new User();
        user.setUsername("小伙子");
        user.setPassword("很嚣张");
        Integer a=userMapper.insert(user);
        System.err.println("受影响的行数有"+a);

    }

}
