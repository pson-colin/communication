package life.senlin.communication.model;

import lombok.Data;

/**
 * @Author: colin
 * @Date: 16:00 2019/11/8
 */
@Data
public class Question {
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

}
