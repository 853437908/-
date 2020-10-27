package cn.tedu.straw.portal.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommentDTO {
    private Integer answerId;
    private String content;
}
