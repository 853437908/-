package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.dto.CommentDTO;
import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.mapper.CommentMapper;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.service.ICommentService;
import cn.tedu.straw.portal.service.ex.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    CommentMapper commentMapper;
    @Override
    public Comment post(CommentDTO commentDTO, Integer userId, String NickName) {
        // 创建Comment对象
        Comment comment=new Comment();
        // 向Comment对象中封装数据：user_id			>>> 参数userId
        comment.setUserId(userId);
        // 向Comment对象中封装数据：user_nick_name	>>> 参数userNickName
        comment.setUserNickName(NickName);
        // 向Comment对象中封装数据：answer_id		>>> commentDTO
        comment.setAnswerId(commentDTO.getAnswerId());
        // 向Comment对象中封装数据：content			>>> commentDTO
        comment.setContent(commentDTO.getContent());
        // 向Comment对象中封装数据：created_time		>>> 创建当前时间对象
        comment.setCreatedTime(LocalDateTime.now());
        // 调用int commentMapper.insert(Comment comment)方法插入评论数据，获取返回的受影响行数
       int rows=commentMapper.insert(comment);
        // 判断返回值是否不为1
        if(rows!=1) {
            // 是：抛出InsertException
            throw new InsertException("评论失败服务器正在忙");
        }
        // 返回Comment对象
         return comment;
    }

    @Override
    public Comment delete(Integer commentId, Integer userId, Integer userType) {
        // 根据参数commentId调用mapper.selectById()查询被删除的“评论”的信息
        Comment comment=commentMapper.selectById(commentId);
        // 判断查询结果是否为null
        if(comment==null) {
            // 是：该“评论”不存在，抛出CommentNotFoundException异常
            throw new CommentNotFoundException("删除失败,该评论不存在");
        }
        // 基于查询结果中的userId，结合参数userId，判断查询结果数据是否是当前登录用户的，
        // 或基于参数userType，判断当前登录的用户的身份是“老师”，
        if(userId.equals(comment.getUserId())&&userType!= User.TYPE_TEACHER ) {
            // 如果这2个条件都不符合，则不允许删除，抛出PermissionDeniedException
            throw new PermissionDeniedException("删除失败,你没有删除此评论的权限");
        }
        // 根据参数commentId调用mapper.deleteById()执行删除，并获取返回的受影响行数
        int rows=commentMapper.deleteById(commentId);
        // 判断返回值是否不为1
        if(rows!=1) {
            // 是：抛出DeleteException
            throw new DeleteException("删除失败,服务器正在忙,请稍后再试");
        }
        // 返回查询结果
        return comment;
    }

    @Override
    public Comment update(Integer commentId, String content, Integer userId, Integer type) {
        // 根据参数commentId调用mapper.selectById()查询被编辑的“评论”的信息
            Comment comment=commentMapper.selectById(commentId);
        // 判断查询结果(result)是否为null
        if(comment==null) {
            // 是：该“评论”不存在，抛出CommentNotFoundException异常
            throw  new CommentNotFoundException("修改失败,当前评论不存在");
        }
        // 基于查询结果中的userId，结合参数userId，判断查询结果数据是否是当前登录用户的，
        // 或基于参数userType，判断当前登录的用户的身份是“老师”，
        if(!comment.getUserId().equals(userId)&&userId!=User.TYPE_TEACHER) {
            // 如果这2个条件都不符合，则不允许删除，抛出PermissionDeniedException
            throw new   PermissionDeniedException("修改失败, 你没有修改的权限");
        }
        // 创建新的Comment comment对象
        Comment result=new Comment();
        // 将commentId, content封装到comment中
        result.setId(commentId);
        result.setContent(content);
        // 根据comment调用mapper.updateById()执行修改，并获取返回的受影响行数
        int rows=commentMapper.updateById(result);
        // 判断返回值是否不为1
        if(rows!=1) {
            // 是：抛出UpdateException
            throw  new UpdateException("修改失败,服务器正在忙,请稍后再试");
        }
        // 将content封装到result中
        comment.setContent(content);
        // 返回查询结果
        return comment;
    }
}
