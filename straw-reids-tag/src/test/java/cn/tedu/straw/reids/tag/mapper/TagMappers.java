package cn.tedu.straw.reids.tag.mapper;

import cn.tedu.straw.commons.dto.co.TagVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TagMappers {
    @Autowired(required = false)
    private TagMapper tagMapper;

    @Test
    public  void aa(){
        List<TagVo> list=   tagMapper.findAll();
        System.err.println(list);
    }
}
