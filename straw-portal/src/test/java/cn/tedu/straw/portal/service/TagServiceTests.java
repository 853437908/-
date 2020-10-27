package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.co.TagVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class TagServiceTests {
    @Autowired
    ITagService service;
    @Test
    void a(){
        List<TagVo>tagVos=service.getTags();
        log.debug("查询到了{}条数据",tagVos.size());
        for (TagVo tagVo : tagVos) {
            log.debug("数据时{}",tagVo);
        }
    }

}
