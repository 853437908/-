package cn.tedu.straw.commons.dto.co;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionVo {

    private Integer id;
    private String title;
    private String content;
    private Integer userId;
    private String userNickName;
    private Integer status;
    private Integer hits;
    private Integer isPublic;
    private Integer isDelete;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private String tagIds;
    private List<TagVo> tags;

}
