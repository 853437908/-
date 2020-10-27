package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.co.AnswerVO;
import cn.tedu.straw.portal.dto.AnswerDTO;
import cn.tedu.straw.portal.model.Answer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-29
 */
public interface IAnswerService extends IService<Answer> {
    /**
     * 提交问题的回复
     * @param answerDTO 客户端提交的回复对象
     * @param userId    当前登录的用户id
     * @param userNickName  当前登录的用户昵称
     * @return 新创建的回答对象
     */
    Answer post(AnswerDTO answerDTO,Integer userId,String userNickName);

    /**
     * 根据问题的id查询回答的列表
     * @param questionId    问题的id
     * @return  该问题的所有回答的列表
     */
    List<AnswerVO> getListByquestionId(Integer questionId);

}
