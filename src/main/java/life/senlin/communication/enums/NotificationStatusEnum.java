package life.senlin.communication.enums;

/**
 * @Author: colin
 * @Date: 13:28 2019/11/23
 */
public enum NotificationStatusEnum {
    UNREAD(0),READ(1);
    private int status;

    public int getStatus() {
        return status;
    }

    NotificationStatusEnum(int status) {
        this.status = status;
    }
}
