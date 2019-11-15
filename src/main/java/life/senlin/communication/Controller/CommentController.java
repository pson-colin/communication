package life.senlin.communication.Controller;

import life.senlin.communication.dto.CommentDTO;
import life.senlin.communication.mapper.CommentMapper;
import life.senlin.communication.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: colin
 * @Date: 16:59 2019/11/15
 */
@Controller
public class CommentController {

    @Autowired
    CommentMapper commentMapper;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setType(commentDTO.getType());
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModify(comment.getGmtCreate());
        comment.setCommentator(33);
        comment.setLikeCount(0L);
        commentMapper.insert(comment);
        return null;
    }
}
