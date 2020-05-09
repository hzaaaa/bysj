package com.hza.bysj.web.backend;

import com.hza.bysj.common.Const;
import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IAnswerService;
import com.hza.bysj.service.IQuestionService;
import com.hza.bysj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/manage/")
public class UserManageController {


    @Autowired
    private IUserService iUserService;
    @Autowired
    private IQuestionService iQuestionService;
    @Autowired
    private IAnswerService iAnswerService;

    @RequestMapping(value = "manageUserList.do/{page}/{size}")
    @ResponseBody
    public ServerResponse<Page<User>> manageUserList(HttpSession session, @PathVariable("page")Integer page, @PathVariable("size")Integer size){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(user.getRole() == 1){
            return iUserService.userList(page, size);
        }else{
            return ServerResponse.createByErrorMessage("用户无权操作");
        }

    }
    @RequestMapping(value = "manageQuestionList.do/{page}/{size}")
    @ResponseBody
    public ServerResponse<Page<Question>> manageQuestionList(HttpSession session, @PathVariable("page")Integer page, @PathVariable("size")Integer size){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        if(user.getRole() == 1){
            return iQuestionService.ManagelistQuestion(page, size);
        }else{
            return ServerResponse.createByErrorMessage("用户无权操作");
        }

    }


}
