package life.senlin.communication.dto;

import life.senlin.communication.model.User;
import lombok.Data;

/**
 * @Author: colin
 * @Date: 15:02 2019/11/19
 */
@Data
public class CommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private String content;
    private Integer commentCount;
    private Long gmtCreate;
    private Long gmtModify;
    private Long likeCount;
    private User user;
}
