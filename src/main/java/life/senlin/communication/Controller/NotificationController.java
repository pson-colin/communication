package life.senlin.communication.Controller;

import life.senlin.communication.dto.NotificationDTO;
import life.senlin.communication.dto.PaginationDTO;
import life.senlin.communication.enums.NotificationTypeEnum;
import life.senlin.communication.model.Notification;
import life.senlin.communication.model.User;
import life.senlin.communication.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: colin
 * @Date: 17:09 2019/11/23
 */
@Controller
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notification/{id}")
    public String notify(@PathVariable("id") Long id,
                         HttpServletRequest request) {
        //校验登录信息
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        //读通知，更新通知为已读状态
        NotificationDTO notificationDTO = notificationService.read(id, user);
        //若该通知的类型是回答问题或回答评论，则跳转到产生该条通知的话题详情页
        if (NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType()
                || NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterid();
        } else {
            return "redirect:/";
        }
    }
}
