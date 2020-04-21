package com.hza.bysj.web.portal;


import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/question/")
public class QuestionController {
    //搜索问题
    //提出问题
    //问题管理
    //获取推送问题

    @Autowired
    private IQuestionService iQuestionService;

    @RequestMapping(value = "ask.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Question> ask_question(String question_title,String question_explain,HttpSession session){



    }

}
