package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.co.QuestionListItemVo;
import cn.tedu.straw.portal.co.QuestionVo;
import cn.tedu.straw.portal.dto.QuestionDTO;
import cn.tedu.straw.portal.model.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-22
 */
public interface IQuestionService extends IService<Question> {

    void create(QuestionDTO questionDTO,Integer uid,String userNickname);

    /**
     * 查询热门的是10条问题
     * @return
     */
    List<QuestionListItemVo> getMostHits();

    /**
     * 查询热门的10条缓存问题
     * @return
     */
    List<QuestionListItemVo> getCachedMostHits();

    /**
     * 获取某用户某页的问题列表
     * @param userId 用户的id
     * @param page  页码
     * @return  匹配的问题列表
     */
    PageInfo<QuestionVo> getQuestionsByUserId(Integer userId,Integer page,Integer type);

    QuestionVo getQuestionById(Integer id);

}
