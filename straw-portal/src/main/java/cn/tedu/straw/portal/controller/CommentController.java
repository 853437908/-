package cn.tedu.straw.portal.controller;


import cn.tedu.straw.portal.co.R;
import cn.tedu.straw.portal.dto.CommentDTO;
import cn.tedu.straw.portal.model.Comment;
import cn.tedu.straw.portal.security.UserInfo;
import cn.tedu.straw.portal.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-29
 */
@RestController
@RequestMapping("/api/v1/comments")
public class CommentController {

    @Autowired
    private ICommentService commentService;
    //http://localhost:8080/api/v1/comments/post?answerId=11&content=机器猫
    @RequestMapping("/post")
    public R<Comment>post(CommentDTO commentDTO, @AuthenticationPrincipal UserInfo userInfo){
        Comment comment=commentService.post(commentDTO,userInfo.getId(),userInfo.getNickname());
        return R.ok(comment);
    }

    @RequestMapping("/{commentId}/delete")
    public R<Comment>delete(@PathVariable("commentId")Integer commentId,@AuthenticationPrincipal UserInfo userInfo ) {
        Comment comment=commentService.delete(commentId,userInfo.getId(),userInfo.getType());
        return R.ok(comment);
    }
    @RequestMapping("/{commentId}/update")
    public R<Comment>update(@PathVariable("commentId") Integer commentId,String content,@AuthenticationPrincipal UserInfo userInfo){
        Comment comment=commentService.update(commentId,content,userInfo.getId(),userInfo.getType());
        return R.ok(comment);
    }



}
