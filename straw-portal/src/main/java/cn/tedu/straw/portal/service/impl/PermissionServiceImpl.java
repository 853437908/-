package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.model.Permission;
import cn.tedu.straw.portal.mapper.PermissionMapper;
import cn.tedu.straw.portal.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-18
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
