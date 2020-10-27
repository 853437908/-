package cn.tedu.straw.portal.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("class_info")
public class ClassInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 班级名称
     */
    @TableField("name")
    private String name;

    /**
     * 邀请码
     */
    @TableField("invite_code")
    private String inviteCode;

    /**
     * 班级是否可用
     */
    @TableField("enabled")
    private Integer enabled;

    @TableField("createdtime")
    private LocalDateTime createdtime;

    @TableField("modifiedtime")
    private LocalDateTime modifiedtime;


}
