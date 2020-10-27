package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.co.TeacherVO;
import cn.tedu.straw.portal.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-14
 */
public interface IUserService extends IService<User> {
    /**
     * 注册学生账号
     * @param user  学生的信息
     * @param inviteCode  邀请码
     */
    public void registerStudent(User user, String inviteCode);

    /**
     * 根据用户名 获取用户详情,将应用于SpringSecuriy验证登录
     * @param username 用户名
     * @return  匹配的用户详情 如果没有匹配的数据,则返回null
     */
    UserDetails login(String username);

    List<TeacherVO> findteachers();

    List<TeacherVO>findCachedTeachers();
}
