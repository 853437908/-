package cn.tedu.straw.portal.dto;

import lombok.Data;

@Data
public class QuestionDTO {
    private String title;
    private Integer[] tagIds;
    private Integer [] teacherIds;
    private String content;
}
