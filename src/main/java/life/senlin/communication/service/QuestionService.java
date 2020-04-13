package life.senlin.communication.service;

import life.senlin.communication.dto.PaginationDTO;
import life.senlin.communication.dto.QuestionDTO;
import life.senlin.communication.dto.QuestionQueryDTO;
import life.senlin.communication.exception.CustomizeErrorCode;
import life.senlin.communication.exception.CustomizeException;
import life.senlin.communication.mapper.QuestionExtMapper;
import life.senlin.communication.mapper.QuestionMapper;
import life.senlin.communication.mapper.UserMapper;
import life.senlin.communication.model.Question;
import life.senlin.communication.model.QuestionExample;
import life.senlin.communication.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: colin
 * @Date: 16:36 2019/11/9
 */
@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;

    //为主页的话题列表进行分页查询
    public PaginationDTO list(String search,Integer page, Integer size) {
        if(StringUtils.isNotBlank(search)){
            //对搜索关键词进行正则处理
            String[] tags = StringUtils.split(search," ");
            search = Arrays.stream(tags).collect(Collectors.joining("|"));
        }
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);

        Integer offset = size * (page - 1);

        //让问题列表按问题的创建时间倒叙排列
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        questionQueryDTO.setSize(size);
        questionQueryDTO.setPage(offset);
        List<Question> questions = questionExtMapper.selectBySearch(questionQueryDTO);
        //将话题对象封装为话题传输对象
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //将话题传输对象放到分页传输对象上
        paginationDTO.setData(questionDTOList);


        return paginationDTO;
    }

    //为用户的个人话题列表进行分页查询，page是指第几页，size指每一页的容量
    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO<>();
        Integer totalPage;//总页数

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(questionExample);//总话题数
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > totalPage) {
            page = totalPage;
        }
        paginationDTO.setPagination(totalPage, page);


        Integer offset = size * (page - 1);

        //查询一页列表上的话题
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions = questionMapper.selectByExampleWithBLOBsWithRowbounds(example, new RowBounds(offset, size));
        //将话题对象封装为话题传输对象
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        //将话题传输对象放到分页传输对象上
        paginationDTO.setData(questionDTOList);


        return paginationDTO;
    }
    //根据话题Id查找话题对象，并将话题对象封装为话题传输对象
    public QuestionDTO findById(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
    //更新或创建话题
    public void createOrUpdate(Question question) {
        if (question.getId() != null) {
            //更新操作
            Question upDateQuestion = new Question();
            upDateQuestion.setTitle(question.getTitle());
            upDateQuestion.setDescription(question.getDescription());
            upDateQuestion.setTag(question.getTag());
            upDateQuestion.setGmtModify(System.currentTimeMillis());

            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExample(upDateQuestion, questionExample);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        } else {
            //创建操作
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModify(question.getGmtCreate());
            questionMapper.insertSelective(question);
        }
    }
    //更新话题浏览数
    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }
    //查找相关话题列表
    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if (StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        //将话题的标签正则处理
        String[] tags = StringUtils.split(queryDTO.getTag(),",");
        String regexTag = Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(regexTag);
        //筛选话题表中拥有一个或多个相同标签的话题
        List<Question> questions = questionExtMapper.selectRelated(question);
        //将话题对象封装为话题传输对象
        List<QuestionDTO> questionDTOs = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOs;
    }
}
