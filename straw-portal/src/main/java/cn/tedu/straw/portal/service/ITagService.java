package cn.tedu.straw.portal.service;

import cn.tedu.straw.portal.co.TagVo;
import cn.tedu.straw.portal.model.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-20
 */
public interface ITagService extends IService<Tag> {
        List<TagVo>getTags();

        List<TagVo>getCachedTags();

        /**
         * 根据标签的id从缓存中获取标签对象
         * @param Tagid  标签的id
         * @return 标签对象
         */
        TagVo getTagVoById(Integer Tagid);

        /**
         * 获取缓存的标签的Map集合
         *
         * @return 缓存的标签的Map集合
         */
        Map<Integer, TagVo> getCachedTagsMap();
}
