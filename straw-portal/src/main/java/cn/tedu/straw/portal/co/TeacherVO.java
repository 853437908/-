package cn.tedu.straw.portal.co;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeacherVO {
    private Integer id;
    private String nickname;
    private String gender;
    private String phone;
}
