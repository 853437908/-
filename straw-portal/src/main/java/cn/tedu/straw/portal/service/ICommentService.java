package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.dto.CommentDTO;
import cn.tedu.straw.portal.model.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-29
 */
public interface ICommentService extends IService<Comment> {

    Comment post(CommentDTO commentDTO,Integer userId,String NickName);

    /**
     * 删除评论
     * @param commentId  评论数据的id
     * @param userId    当前登录的用户的id
     * @param userType  当前登录的用户的类型
     * @return  成功删除的评论数据
     */
    Comment delete(Integer commentId,Integer userId,Integer userType);

    /**
     * 修改评论
     * @param commentId  评论数据的id
     * @param content   评论的内容
     * @param userId    用户的id
     * @param type      用户的状态
     * @return
     */
    Comment update(Integer commentId,String content,Integer userId,Integer type);

}
