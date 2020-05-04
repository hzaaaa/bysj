package com.hza.bysj.web.portal;


import com.hza.bysj.common.Const;
import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.common.util;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IQuestionService;
import com.hza.bysj.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/question/")
public class QuestionController {
    //搜索问题
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

    @RequestMapping(value = "my_questions.do/{page}/{size}")
    @ResponseBody
    public ServerResponse<Page<Question>> my_questions(@PathVariable("page")Integer page, @PathVariable("size")Integer size, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");
        Pageable pageable =  PageRequest.of(page, size);
        ServerResponse<List<Question>> listServerResponse = iQuestionService.list_questionByUser(user);
        Page<Question> questions = util.listConvertToPage(listServerResponse.getData(), pageable);
        return ServerResponse.createBySuccess(questions);
    }

    @RequestMapping(value = "deleteById.do/{id}")
    @ResponseBody
    public ServerResponse<String> deleteById (HttpSession session,@PathVariable("id")Integer id){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");

        ServerResponse<String> stringServerResponse = iQuestionService.delete_question(id, user);
        //session.setAttribute(Const.CURRENT_USER,user);
        return stringServerResponse;

    }

    @RequestMapping(value = "getquestion_byId.do/{id}")
    @ResponseBody
    public ServerResponse<Question> getquestion_byId(@PathVariable("id")Integer id){
        return iQuestionService.look_question(id);
    }

    @RequestMapping(value = "update_question.do")
    @ResponseBody
    public ServerResponse<Question> update_question(@RequestBody Map map, HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user == null) return ServerResponse.createByErrorMessage("用户未登录");

        ServerResponse<Question> questionServerResponse = iQuestionService.update_question((Integer) map.get("id"), user, (String) map.get("question_explain"));
        return questionServerResponse;


    }

    @RequestMapping(value = "search.do")
    @ResponseBody
    public ServerResponse<List<Question>>search(@RequestBody Question question)  {
        return util.search(question.getQuestion_explain());
    }

}
