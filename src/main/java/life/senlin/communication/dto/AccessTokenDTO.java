package life.senlin.communication.dto;

import lombok.Data;

/**
 * @Author: colin
 * @Date: 15:11 2019/11/6
 */
@Data
public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;
}
