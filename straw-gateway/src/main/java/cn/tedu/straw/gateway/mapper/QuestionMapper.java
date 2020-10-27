package cn.tedu.straw.gateway.mapper;

import cn.tedu.straw.portal.co.QuestionListItemVo;
import cn.tedu.straw.portal.co.QuestionVo;
import cn.tedu.straw.portal.model.Question;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-22
 */
@Repository
public interface QuestionMapper extends BaseMapper<Question> {
    /**
     * 查询热门问题列表
     * @return
     */
    List<QuestionListItemVo>findMostHits();

    /**
     * 查询某用户的问题列表
     * @param userId    用户的ID
     * @return  返回该用户的问题列表
     */
    List<QuestionVo>findListByUserId(Integer userId);

    /**
     * 查询老师应该回答的问题
     * @param teacherId
     * @return 返回问题列表
     */
    List<QuestionVo>findTeacherQuestions(Integer teacherId);

    /**
     * 查询问题详情
     * @param id    问题的id
     * @return  返回问题详情
     */
    QuestionVo findByid(Integer id);

}
