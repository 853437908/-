package cn.tedu.straw.portal.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class AnswerDTO {
    @NotNull(message = "问题id不允许为空")
    private Integer questionId;
    @NotBlank(message = "必须填写回复的内容")
    private String content;
}
