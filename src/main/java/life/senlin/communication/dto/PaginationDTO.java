package life.senlin.communication.dto;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: colin
 * @Date: 12:50 2019/11/10
 */
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;
    private boolean showFirstPage;
    private boolean showEndPage;
    private boolean showNext;
    private Integer currentPage;
    private Integer totalPage;
    private List<Integer> pages = new ArrayList<Integer>();

    public void setPagination(Integer totalCount, Integer page, Integer size) {

        //总页数
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }
        if(page < 1){
            page = 1;
        }
        if(page > totalPage){
            page = totalPage;
        }
        this.currentPage = page;
        //初始化pages数组
        pages.add(page);
        for (int i = 1; i <= 3; i++) {
            if (page - i > 0) {
                pages.add(0,page - i);
            }
            if(page + i <= totalPage){
                pages.add(page + i);
            }

        }
        //是否展示上一页按钮
        if (page == 1) {
            showPrevious = false;
        } else {
            showPrevious = true;
        }
        //是否展示下一页按钮
        if (page == totalPage) {
            showNext = false;
        } else {
            showNext = true;
        }
        //是否展示第一页按钮
        if (pages.contains(1)) {
            showFirstPage = false;
        } else {
            showFirstPage = true;
        }
        //是否展示最后一页按钮
        if (pages.contains(totalPage)) {
            showEndPage = false;
        } else {
            showEndPage = true;
        }
    }
}
