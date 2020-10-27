package cn.tedu.straw.commons.dto.co;


import cn.tedu.straw.commons.dto.model.Comment;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Accessors(chain = true)
public class AnswerVO {
    private Integer id;
    private String content;
    private Integer countOfLikes;
    private Integer userId;
    private String userNickName;
    private Integer questionId;
    private LocalDateTime createdTime;
    private Integer statusOfAccept;
    private List<Comment>comments;

}
