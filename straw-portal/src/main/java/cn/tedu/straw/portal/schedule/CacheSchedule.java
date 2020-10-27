package cn.tedu.straw.portal.schedule;

import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.ITagService;
import cn.tedu.straw.portal.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
@Slf4j
public class CacheSchedule {
    @Autowired
    private ITagService tagService;
    @Autowired
    private IUserService userService;
    /**
     * 缓存锁 ,凡是写入(移除,添加)缓存的数据使用这个锁
     * publig :多个类都需要使用这把锁
     * static 具有唯一的特性
     * final  不让其改变
     * Object 表示可以是任何类型
     */
    public static final  Object LOCK_CACHE=new Object();
    public static final  Object LOCK_CACHE_QUESTION=new Object();

    @Scheduled(initialDelay = 10*60*1000,fixedRate = 10*60*1000)
    public void clearCache(){
       synchronized (LOCK_CACHE){
           tagService.getCachedTags().clear();
           tagService.getCachedTagsMap().clear();
           userService.findCachedTeachers().clear();
           log.debug("clear tags cache ...");
       }
    }

    @Autowired
    IQuestionService questionService;
    @Scheduled(initialDelay = 1*60*1000,fixedRate = 1*60*1000)
    public  void clearQuestion(){
        synchronized (LOCK_CACHE_QUESTION){
            questionService.getCachedMostHits().clear();
        }
    }


}
