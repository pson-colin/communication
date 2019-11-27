package life.senlin.communication.dto;

import lombok.Data;

/**
 * @Author: colin
 * @Date: 14:02 2019/11/27
 */
@Data
public class QuestionQueryDTO {
    private String search;
    private Integer page;
    private Integer size;
}
