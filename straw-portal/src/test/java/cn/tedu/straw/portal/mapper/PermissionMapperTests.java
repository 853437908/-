package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Permission;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class PermissionMapperTests {

    @Autowired
    PermissionMapper mapper;

    @Test
    void aa(){
        List<Permission>list=mapper.selectByUserId(1);
        log.debug("permissions count={}", list.size());
        for (Permission permission : list) {
            log.debug("permission > {}", permission);
        }
    }


}
