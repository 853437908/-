package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.co.QuestionVo;
import cn.tedu.straw.portal.dto.QuestionDTO;
import cn.tedu.straw.portal.service.ex.ServiceException;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
public class QuestionServiceTests {

    @Autowired
    IQuestionService service;

    @Test
    void create(){
        try {
            QuestionDTO questionDTO = new QuestionDTO();
            questionDTO.setTitle("SpringSecurity验证时记录了用户的ID吗？");
            questionDTO.setContent("SpringSecurity自动完成验证，可以获取用户名，但是，用户ID在哪里获取？");
            questionDTO.setTagIds(new Integer[] { 5, 8, 13 });
            questionDTO.setTeacherIds(new Integer[] { 2, 3 });
            Integer userId = 5;
            String userNickname = "超级码农";
            service.create(questionDTO, userId, userNickname);
            log.debug("create question ok.");
            log.debug("创建成功");
        }catch (ServiceException e){
            log.debug("create question failure.", e);
        }
    }
    @Test
    void getQuestionsByUserId(){
        PageInfo<QuestionVo> questionVos =service.getQuestionsByUserId(6,0,1);
        System.err.println("小伙子很嚣张啊11111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        log.debug("page info >>> {}", questionVos);
    }
    @Test
    void a(){
        QuestionVo questionVo=service.getQuestionById(1);
        log.debug("查询的结果是{}",questionVo);
    }



}
