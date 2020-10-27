package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.co.TagVo;
import cn.tedu.straw.portal.model.Tag;
import cn.tedu.straw.portal.mapper.TagMapper;
import cn.tedu.straw.portal.schedule.CacheSchedule;
import cn.tedu.straw.portal.service.ITagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-20
 */
@Service
@Slf4j
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

    @Autowired
    TagMapper tagMapper;

    /**
     * 缓存的标签列表
     */
    private List<TagVo>tags=new CopyOnWriteArrayList<>();//读写分离的list能保证数据的安全

    @Override
    public List<TagVo> getTags() {
        //判断有没有有必要锁住代码
       if(tags.isEmpty()){
           //锁住代码
           synchronized (CacheSchedule.LOCK_CACHE){
               //判断有没有必要重新加载数据
               if(tags.isEmpty()){
               //    tags=tagMapper.findAll();
                   tags.addAll(tagMapper.findAll());
                   log.debug("create tags cache ...");
                   log.debug(">>> tags : {}", tags);
                   for (TagVo tag : tags) {
                       tagsMap.put(tag.getId(),tag);
                   }
                   log.debug("create tags map cache ...");
                   log.debug(">>> tags map : {}", tagsMap);
               }
           }
       }
        return tags;

    }

    @Override
    public List<TagVo> getCachedTags() {
        return tags;
    }

    /**
     * 缓存的标签map集合
     */
    private  Map<Integer,TagVo> tagsMap=new ConcurrentHashMap<>();

    @Override
    public TagVo getTagVoById(Integer tagid) {
        // 如果缓存数据不存在，调用以上方法从数据库中读取数据并缓存下来
        if (tagsMap.isEmpty()) {
            getTags();
        }
        // 从缓存的Map中取出数据
        TagVo tag = tagsMap.get(tagid);
        // 返回
        return tag;
    }

    @Override
    public Map<Integer, TagVo> getCachedTagsMap() {
        return tagsMap;
    }
}
