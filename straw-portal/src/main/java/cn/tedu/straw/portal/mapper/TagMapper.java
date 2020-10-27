package cn.tedu.straw.portal.mapper;

import cn.tedu.straw.portal.co.TagVo;
import cn.tedu.straw.portal.model.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-20
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {
    /**
     * 查询所有标签数据
     * @return  返回所有标签数据列表
     */
    List<TagVo>findAll();

}
