package life.senlin.communication.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: colin
 * @Date: 15:59 2019/11/22
 */
@Data
public class TagDTO {
    private String categoryName;
    private List<String> tags;
}
