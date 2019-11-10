package life.senlin.communication.dto;

import lombok.Data;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: colin
 * @Date: 15:51 2019/11/6
 */
@Data
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatarUrl;

}
