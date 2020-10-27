package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.co.TeacherVO;
import cn.tedu.straw.portal.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-15
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 查询所有的老师信息
     * @return
     */
    List<TeacherVO>findTeachers();

}
