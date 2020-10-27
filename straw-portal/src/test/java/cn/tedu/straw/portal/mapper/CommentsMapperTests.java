package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.model.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class CommentsMapperTests {
    @Autowired
    CommentMapper mapper;
    @Test
    void deleteById(){
        Integer id=1;
        int rows=mapper.deleteById(id);
        log.debug("删除成功{}",rows);
    }

    @Test
    void selectByid(){
        Comment comment=mapper.selectById(2);
        log.debug("查询到的数据时{}",comment);
    }



}
