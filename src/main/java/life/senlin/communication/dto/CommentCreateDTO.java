package life.senlin.communication.dto;

import lombok.Data;

/**
 * @Author: colin
 * @Date: 17:01 2019/11/15
 */
@Data
public class CommentCreateDTO {
    private Long parentId;
    private String content;
    private Integer type;
}
