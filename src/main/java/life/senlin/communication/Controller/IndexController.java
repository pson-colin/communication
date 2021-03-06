package life.senlin.communication.Controller;

import life.senlin.communication.dto.PaginationDTO;
import life.senlin.communication.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: colin
 * @Date: 11:41 2019/11/6
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    //首页话题列表加载
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search) {
        PaginationDTO pagination = questionService.list(search,page,size);
        model.addAttribute("pagination", pagination);
        model.addAttribute("search",search);
        return "index";
    }
}
