package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
@Slf4j
public class UserMapperTest {

    @Autowired
    DataSource dataSource;
    @Autowired
    UserMapper userMapper;
    @Test
    void hehe() throws SQLException {
        Connection connection=dataSource.getConnection();
        log.debug("{}",connection);
    }

    @Test
    void selectById(){
      User user=userMapper.selectById(1);
      log.debug("user={}",user);
    }


}
