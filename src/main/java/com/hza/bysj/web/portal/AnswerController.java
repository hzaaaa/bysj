package com.hza.bysj.web.portal;


import com.hza.bysj.common.Const;
import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.Answer;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IAnswerService;
import com.hza.bysj.service.IInviteService;
import com.hza.bysj.service.ILikeService;
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
    @Autowired
    private ILikeService iLikeService;

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
    @RequestMapping(value = "my_answers.do")
    @ResponseBody
    public ServerResponse<List<Answer>> my_answers(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");

        return iAnswerService.list_answerByUser(user);
    }

    @RequestMapping(value = "deleteById.do/{id}")
    @ResponseBody
    public ServerResponse<String> deleteAnswerById(@PathVariable("id")Integer id,HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");

        return iAnswerService.delete_answer(id,user);
    }

    @RequestMapping(value = "updateAnswer.do")
    @ResponseBody
    public ServerResponse<Answer> updateAnswer(HttpSession session,@RequestBody Answer answer){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");

        return iAnswerService.update_answer(answer.getId(),user,answer.getAnswer());
    }

    @RequestMapping(value = "look_answerById.do/{id}")
    @ResponseBody
    public ServerResponse<Answer> lookAnswerById(@PathVariable("id")Integer id){
        return iAnswerService.look_answer(id);
    }

    @RequestMapping(value = "push_answer")
    @ResponseBody
    public ServerResponse<List<Answer>> push_answer(){
        return iAnswerService.push_answer();
    }

    @RequestMapping(value = "like/{id}")
    @ResponseBody
    public ServerResponse<String> like(HttpSession session,@PathVariable("id")Integer answer_id){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");

        return iLikeService.like(user,answer_id);
    }
    @RequestMapping(value = "dislike/{id}")
    @ResponseBody
    public ServerResponse<String> dislike(HttpSession session,@PathVariable("id")Integer answer_id){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");

        return iLikeService.dislike(user,answer_id);
    }




}
