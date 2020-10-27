package cn.tedu.straw.portal.service.impl;

import cn.tedu.straw.portal.co.QuestionListItemVo;
import cn.tedu.straw.portal.co.QuestionVo;
import cn.tedu.straw.portal.co.TagVo;
import cn.tedu.straw.portal.dto.QuestionDTO;
import cn.tedu.straw.portal.mapper.QuestionTagMapper;
import cn.tedu.straw.portal.mapper.UserQuestionMapper;
import cn.tedu.straw.portal.model.Question;
import cn.tedu.straw.portal.mapper.QuestionMapper;
import cn.tedu.straw.portal.model.QuestionTag;
import cn.tedu.straw.portal.model.User;
import cn.tedu.straw.portal.model.UserQuestion;
import cn.tedu.straw.portal.schedule.CacheSchedule;
import cn.tedu.straw.portal.service.IQuestionService;
import cn.tedu.straw.portal.service.ITagService;
import cn.tedu.straw.portal.service.ex.InsertException;
import cn.tedu.straw.portal.service.ex.QuestionNotFoundException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author tedu.cn
 * @since 2020-07-22
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {


    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionTagMapper questionTagMapper;
    @Autowired
    private UserQuestionMapper userQuestionMapper;

    @Override
    public void create(QuestionDTO questionDTO,Integer userid,String userNickname) {
        // 创建当前时间对象：now
        LocalDateTime now = LocalDateTime.now();

        // 将questionDTO中的tagIds转换成例如 2,7,9 这种格式的字符串，名为tagIdsStr
        String tagIdsStr = Arrays.toString(questionDTO.getTagIds());    // [2, 7, 9]
        tagIdsStr = tagIdsStr.substring(1, tagIdsStr.length() - 1);     // 2, 7, 9

        // 创建Question对象
        // 向Question对象中补全数据
        // - title / content > questionDTO的title / content
        // - userId / userNickName > 参数
        // - status > 0
        // - hits > 0
        // - isPublic > 1
        // - createdTime / modifiedTime > now
        // - isDelete > 0
        // - tagIds > tagIdsStr
        Question question = new Question()
                .setTitle(questionDTO.getTitle())
                .setContent(questionDTO.getContent())
                .setUserId(userid)
                .setUserNickName(userNickname)
                .setStatus(0).setHits(0).setIsPublic(1).setIsDelete(0)
                .setCreatedTime(now).setModifiedTime(now)
                .setTagIds(tagIdsStr);
        // 基于以上Question对象，调用questionMapper的insert()方法，向question表中插入数据，获取返回值
        int rows = questionMapper.insert(question);
        // 判断返回值是否不为1
        if (rows != 1) {
            // 是：抛出InsertException
            throw new InsertException("发布问题失败！当前服务器忙，请稍后再尝试！");
        }

        // 遍历questionDTO中的tagIds
        for (Integer tagId : questionDTO.getTagIds()) {
            // - 创建QuestionTag对象
            // - 补全属性：questionId > 以上插入Question对象的id
            // - 补全属性：tagId > 被遍历到的数据
            QuestionTag questionTag = new QuestionTag()
                    .setQuestionId(question.getId())
                    .setTagId(tagId);
            // - 基于以上QuestionTag对象，调用questionTagMapper的insert()方法，向question_tag表中插入数据，以记录“问题”与“标签”的对应关系，并需要获取当前调用方法的返回值
            rows = questionTagMapper.insert(questionTag);
            // - 判断返回值是否不为1
            if (rows != 1) {
                // 是：抛出InsertException
                throw new InsertException("发布问题失败！当前服务器忙，请稍后再尝试！");
            }
        }

        // 遍历questionDTO中的teacherIds
        for (Integer teacherId : questionDTO.getTeacherIds()) {
            // - 创建UserQuestion对象
            // - 补全属性：questionId > 以上插入Question对象的id
            // - 补全属性：userId > 被遍历到的数据
            // - 补全属性：createdTime > now
            UserQuestion userQuestion = new UserQuestion()
                    .setQuestionId(question.getId())
                    .setUserId(teacherId)
                    .setCreatedTime(now);
            // - 基于以上UserQuestion对象，调用userQuestionMapper的insert()方法，向user_question表中插入数据，以记录“问题”与“回答问题的老师”的对应关系，并需要获取当前调用方法的返回值
            rows = userQuestionMapper.insert(userQuestion);
            // - 判断返回值是否不为1
            if (rows != 1) {
                // 是：抛出InsertException
                throw new InsertException("发布问题失败！当前服务器忙，请稍后再尝试！");
            }
        }
    }
    List<QuestionListItemVo>questions=new CopyOnWriteArrayList<>();

    @Override
    public List<QuestionListItemVo> getMostHits() {
        //判断是否需要加锁
        if(questions.isEmpty()){
            synchronized(CacheSchedule.LOCK_CACHE_QUESTION){
                //判断是否需要重新加载数据
                if(questions.isEmpty()){
                    questions.addAll(questionMapper.findMostHits());
                }
            }
        }
        return questions;
    }

    @Override
    public List<QuestionListItemVo> getCachedMostHits() {
        return questions;
    }
    @Value("${project.question.listpage-size}")
    private Integer pageSize;
    @Autowired
    private ITagService tagService;
    @Override
    public PageInfo<QuestionVo> getQuestionsByUserId(Integer userId, Integer page,Integer type) {
        //设置分页参数
        PageHelper.startPage(page,pageSize);
        // 调用持久层方法查询问题列表，该列表中的数据只有标签的id，并不包括标签数据
        List<QuestionVo> questions;
        if(type== User.TYPE_STUDENT){
            questions=questionMapper.findListByUserId(userId);
        }else{
            questions=questionMapper.findTeacherQuestions(userId);
        }
        // 遍历以上列表，取出每个问题中记录的标签的ids，并根据这些id从缓存中取出TagVO封装到QuestionVO对象中
        for (QuestionVo question : questions) {
            question.setTags(getTagsByIds(question.getTagIds()));
        }
        // 返回
        return new PageInfo<>(questions);
    }

    @Override
    public QuestionVo getQuestionById(Integer id) {
        //先通过持久层数据,并判断查询结果是否为null,如果是null 则抛出异常
        QuestionVo questionVo=questionMapper.findByid(id);
        if(questionVo==null){
            throw  new QuestionNotFoundException("该问题没有找到或已被删除");
        }
        //在返回查询结果之前,还应该根据查询结果中的tagIds确定tags的值
        questionVo.setTags(getTagsByIds(questionVo.getTagIds()));

        return questionVo;
    }

    /**
     * 根据标签id获取_(TagVO)数据的集合
     * @param tagIdsStr 有若干个标签id组成的字符串 各id之间使用, 分割
     * @return  标签数据的集合
     */
    private List<TagVo>getTagsByIds(String tagIdsStr){

        // 拆分
        String[] tagIds = tagIdsStr.split(", ");
        // 创建用于存放若干个标签的集合
        List<TagVo>tags=new ArrayList<>();
        // 遍历数组，从缓存中找出对应的TagVO
        for (String tagId : tagIds) {
            // 从缓存中取出对应的TagVO
            Integer id = Integer.valueOf(tagId);
            TagVo tag = tagService.getTagVoById(id);
            // 将取出的TagVO添加到QuestionVO对象中
            tags.add(tag);

        }
        return tags;
    }
}
