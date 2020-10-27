package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.co.QuestionListItemVo;
import cn.tedu.straw.portal.co.QuestionVo;
import cn.tedu.straw.portal.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class QuestionMapperTests {
    @Autowired
    QuestionMapper mapper;

    @Test
    void findMostHits(){
        List<QuestionListItemVo> questions=mapper.findMostHits();
        log.debug("查询到了{}条数据",questions.size());
        for (QuestionListItemVo question : questions) {
            log.debug(">>>{}",question);
        }
    }

    @Test
    void findListByUserId() {
        Integer userId = 9;
        List<QuestionVo> questions
                = mapper.findListByUserId(userId);
        log.debug("question count={}", questions.size());
        for (QuestionVo question : questions) {
            log.debug(">>> {}", question);
        }
    }
    @Test
    void findTeacherQuestions(){
        List<QuestionVo>questions=mapper.findTeacherQuestions(6);
        log.debug("查询到的数据有{}条",questions.size());
        for (QuestionVo question : questions) {
            log.debug("数据是>>>{}",question);
        }
    }
    @Test
    void aaa(){
        QuestionVo question=mapper.findByid(7);
        log.debug("查询到的问题数据>>>>{}",question);
    }



}
