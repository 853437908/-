package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.co.AnswerVO;
import cn.tedu.straw.portal.model.Answer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-29
 */
@Repository
public interface AnswerMapper extends BaseMapper<Answer> {
    /**
     * 根据问题的id查询回答的列表
     * @param questionId    问题的id
     * @return  该问题的所有回答的列表
     */
    List<AnswerVO>findListByQuestionId(Integer questionId);
}
