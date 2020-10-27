package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.co.AnswerVO;
import cn.tedu.straw.portal.dto.AnswerDTO;
import cn.tedu.straw.portal.service.ex.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class AnswerServiceTests {
    @Autowired
    IAnswerService  service;

    @Test
    void aa(){
        try {
            AnswerDTO answerDTO=new AnswerDTO().setContent("时光倒流").setQuestionId(1);
            service.post(answerDTO,1,"李");
            log.debug("执行成功");
        }catch (ServiceException e){
            log.debug("执行失败{}",e.getMessage());
        }

    }

    @Test
    void findListByQuestionId(){
        List<AnswerVO> answers= service.getListByquestionId(11);
        log.debug("找到了{}条内容",answers.size());
        for (AnswerVO answer : answers) {
            log.debug("数据是>>>{}",answer);
        }
    }

}
