package cn.tedu.straw.gateway.service.impl;

import cn.tedu.straw.portal.co.TeacherVO;
import cn.tedu.straw.portal.mapper.ClassInfoMapper;
import cn.tedu.straw.portal.mapper.PermissionMapper;
import cn.tedu.straw.portal.mapper.UserMapper;
import cn.tedu.straw.portal.mapper.UserRoleMapper;
import cn.tedu.straw.portal.model.ClassInfo;
import cn.tedu.straw.portal.model.Permission;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.model.UserRole;
import cn.tedu.straw.portal.schedule.CacheSchedule;
import cn.tedu.straw.portal.security.UserInfo;
import cn.tedu.straw.portal.service.IUserService;
import cn.tedu.straw.portal.service.ex.ClassDisabledException;
import cn.tedu.straw.portal.service.ex.InsertException;
import cn.tedu.straw.portal.service.ex.InviteCodeException;
import cn.tedu.straw.portal.service.ex.PhoneDuplicateException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ClassInfoMapper classInfoMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    PermissionMapper permissionMapper;

    PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();
    @Transactional
    public void registerStudent(User user, String inviteCode){
        // 【由于当前项目设计的规则是“学生账号通过手机号码注册、登录”，必须保证手机号码唯一】
        // 调用ClassInfoMapper对象的selectOne()方法，根据参数inviteCode邀请码，查询class_info表
        QueryWrapper<ClassInfo>classqueryWrapper=new QueryWrapper<>();
        classqueryWrapper.eq("invite_code",inviteCode);
        ClassInfo classInfo=classInfoMapper.selectOne(classqueryWrapper);
        // 判断查询结果是否为空
        if(classInfo==null) {
            // 是：表示没有找到有效的邀请码，不允许注册，抛出InviteCodeException
            throw new InviteCodeException("注册失败！邀请码错误！");
        }
        // 从以上查询到的班级信息中取出enabled，判断是否为0
        if(classInfo.getEnabled()==0) {
            // 是：表示该班级已禁用，不允许注册该班级的学生账号，抛出ClassDisabledException
            throw  new ClassDisabledException("注册失败！班级已经被禁用！");
        }
        // 从参数user中取出手机号码
        String phone=user.getPhone();
        // 调用UserMapper对象的selectOne()方法，根据手机号码查询学生账号信息
        QueryWrapper<User>userQueryWrapper=new QueryWrapper<>();
        userQueryWrapper.eq("phone",phone);
        User result=userMapper.selectOne(userQueryWrapper);
        // 判断查询结果是否不为null
        if(result!=null) {
            // 是：找到了学生信息，表示手机号码已经被占用，则不允许注册，抛出PhoneDuplicateException
            throw  new PhoneDuplicateException("注册失败！ 手机号码已经被注册！");
        }
        // 没有找到学生信息，表示手机号码没有被占用，则允许注册……
        // 确保参数user中的数据全部是有效的
        // - 取出参数user中的密码，调用私有的encode()方法进行加密，并将加密后的密码封装回到user中
        String encodePassword=encode(user.getPassword());
        user.setPassword(encodePassword);
        // - classId：此前验证邀请时得到的结果
        user.setClassId(classInfo.getId());
        // - createdTime：当前时间，LocaleDateTime.now()
        user.setCreatedTime(LocalDateTime.now());
        // - enabled：1，允许使用
        user.setEnabled(1);
        // - locked：0，不锁定
        user.setLocked(0);
        // - type：0，表示学生
        user.setType(0);
        //将手机号作为用户名
        user.setUsername(user.getPhone());
        // 调用UserMapper对象的insert()方法，根据参数user插入数据，获取返回值
        int rows=userMapper.insert(user);
        // 判断返回值（受影响的行数）是否不为1
        if(rows!=1) {
            // 是：受影响的行数不是1，则插入用户数据失败，抛出InsertException
            throw new InsertException("注册失败！服务器忙，请稍后再次尝试！");
        }
        //向用户角色表中插入数据 为当前账号分配角色
        UserRole userRole=new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(2);//角色的id固定为2
        rows=userRoleMapper.insert(userRole);
        //判断返回值是否不为1
        if(rows!=1){
            //是:受影响的行数不是1,则插入用户角色数据失败,抛出异常
            throw  new InsertException("注册失败,服务器忙,请稍后再尝试");
        }

    }




    @Override
    public UserDetails login(String username) {
        //根据参数username查询用户信息
        QueryWrapper<User>queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user=userMapper.selectOne(queryWrapper);
        //判断查询结构是否为Null 有没有这个用户
        //注意: 最终的界面是有Spirng_Security显示的 不要抛异常
        if(user==null){
            return  null;
        }
        List<Permission> permissions=permissionMapper.selectByUserId(user.getId());
        //权限字符串数组
        String[]authorities= new String[permissions.size()];
        for (int i = 0; i < permissions.size(); i++) {
            authorities[i]=permissions.get(i).getName();
        }

        //组织用户详情的对象
//        UserDetails userDetails= org.springframework.security.core.userdetails.User.builder()
//                //TODO
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .authorities(authorities)
//                .disabled(user.getEnabled()==0)
//                .accountLocked(user.getLocked()==1)
//                .build();
        UserInfo userInfo=new UserInfo(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled()==1,
                true,
                true,
                user.getLocked()==0,
                AuthorityUtils.createAuthorityList(authorities)
        );
        userInfo.setId(user.getId());
        userInfo.setNickname(user.getNickname());
        userInfo.setGender(user.getGender());
        userInfo.setType(user.getType());
        return userInfo;
    }

    private List<TeacherVO>teachers=new CopyOnWriteArrayList<>();

    @Override
    public List<TeacherVO> findteachers() {
        if(teachers.isEmpty()){
            synchronized (CacheSchedule.LOCK_CACHE){
                if(teachers.isEmpty()){
                    teachers.addAll(userMapper.findTeachers());
                }
            }
        }
        return teachers;
    }

    @Override
    public List<TeacherVO> findCachedTeachers() {
        return teachers;
    }

    //将密码进行加密的方法
    private String encode(String rawPassword){
          String encodePassword=   passwordEncoder.encode(rawPassword);
          encodePassword="{bcrypt}"+encodePassword;
          return  encodePassword;
    }















}
