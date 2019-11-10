package life.senlin.communication.dto;

import life.senlin.communication.model.User;
import lombok.Data;

/**
 * @Author: colin
 * @Date: 16:35 2019/11/9
 */
@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer creator;
    private Integer viewCount;
    private Integer commentCount;
    private Integer likeCount;
    private User user;

}
