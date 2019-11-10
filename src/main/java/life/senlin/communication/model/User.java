package life.senlin.communication.model;

import lombok.Data;

/**
 * @Author: colin
 * @Date: 17:08 2019/11/7
 */
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreate;
    private Long gmtModify;
    private String avatarUrl;

}
