package com.hza.bysj.web.portal;


import com.hza.bysj.common.Const;
import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IQuestionService;
import com.hza.bysj.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/question/")
public class QuestionController {
    //搜索问题
    //提出问题
    //问题管理
    //获取推送问题

    @Autowired
    private IQuestionService iQuestionService;

    @Autowired
    private ITagService iTagService;

    @RequestMapping(value = "ask.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<Question> ask_question(@RequestBody Map map, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");
        ServerResponse<Tag> tag = iTagService.add_tag(user, (String) map.get("tag"));
        ServerResponse<Question> questionServerResponse = iQuestionService.ask_question((String) map.get("question_title"), (String) map.get("question_explain"), tag.getData(), user);

        return  questionServerResponse;

    }

    @RequestMapping(value = "my_questions.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<List<Question>> my_questions(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");
        return ServerResponse.createBySuccess(user.getQuestionlist());
    }
}
