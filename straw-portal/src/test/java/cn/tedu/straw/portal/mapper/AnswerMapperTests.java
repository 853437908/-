package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.co.AnswerVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class AnswerMapperTests {

    @Autowired
    AnswerMapper mapper;
    @Test
    void findListByQuestionId(){
       List<AnswerVO> answers= mapper.findListByQuestionId(11);
       log.debug("找到了{}条内容",answers.size());
        for (AnswerVO answer : answers) {
            log.debug("数据是>>>{}",answer);
        }
    }


}
