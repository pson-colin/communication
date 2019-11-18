package life.senlin.communication.service;

import life.senlin.communication.enums.CommentTypeEnum;
import life.senlin.communication.exception.CustomizeErrorCode;
import life.senlin.communication.exception.CustomizeException;
import life.senlin.communication.mapper.CommentMapper;
import life.senlin.communication.mapper.QuestionExtMapper;
import life.senlin.communication.mapper.QuestionMapper;
import life.senlin.communication.model.Comment;
import life.senlin.communication.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: colin
 * @Date: 17:12 2019/11/16
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);

        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if (question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            question.setCommentCount(1);
            commentMapper.insert(comment);
            questionExtMapper.incComment(question);
        }
    }
}
