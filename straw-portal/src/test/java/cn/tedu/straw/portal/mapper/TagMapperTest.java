package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.co.TagVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class TagMapperTest {
    @Autowired
    TagMapper mapper;

    @Test
    void aa(){
        List<TagVo>tagVos =mapper.findAll();
        log.debug("查询到了{}条数据",tagVos.size());
        for (TagVo tagvo:tagVos) {
            log.debug("数据有{}",tagvo);
        }
    }
}
