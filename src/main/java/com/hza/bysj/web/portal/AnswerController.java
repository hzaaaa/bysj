package com.hza.bysj.web.portal;


import com.hza.bysj.common.Const;
import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.Answer;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/answer/")
public class AnswerController {
    //回答问题
    //管理回答
    //获取推送回答
    @Autowired
    private IAnswerService iAnswerService;

    @RequestMapping(value = "add_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Answer> add_answer(@RequestBody Map map, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");

        return iAnswerService.answer_question( (Integer) map.get("question_id"),user,(String)map.get("answer"));
    }
    @RequestMapping(value = "look_answers.do/{id}")
    @ResponseBody
    public ServerResponse<List<Answer>> look_answers(@PathVariable("id")Integer id){
        System.out.println(id);
        return  iAnswerService.list_answerByQuestionId(id);
    }


}
