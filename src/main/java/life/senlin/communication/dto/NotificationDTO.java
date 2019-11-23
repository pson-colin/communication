package life.senlin.communication.dto;

import life.senlin.communication.model.User;
import lombok.Data;

/**
 * @Author: colin
 * @Date: 15:28 2019/11/23
 */
@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;
    private String notifierName;
    private String outerTitle;
    private Long outerid;
    private String typeName;
    private Integer type;
}
