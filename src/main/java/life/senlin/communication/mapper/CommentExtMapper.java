package life.senlin.communication.mapper;

import life.senlin.communication.model.Comment;
import life.senlin.communication.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incComment(Comment comment);
}