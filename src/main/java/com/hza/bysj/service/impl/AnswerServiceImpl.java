package com.hza.bysj.service.impl;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.dao.AnswerDAO;
import com.hza.bysj.dao.QuestionDAO;
import com.hza.bysj.pojo.Answer;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class AnswerServiceImpl implements IAnswerService {

    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private AnswerDAO answerDAO;

    @Override
    public ServerResponse<Answer> answer_question(Integer question_id, User user, String answer) {
        Question question = questionDAO.findById(question_id).get();
        if(question==null)
            return ServerResponse.createByErrorMessage("问题不存在");
        Answer answer1 = new Answer();
        answer1.setAnswer(answer);
        answer1.setDate(new Date());
        answer1.setDislike_count(0);
        answer1.setLike_count(0);
        answer1.setQuestion(question);
        answer1.setUser(user);
        answerDAO.save(answer1);
        return null;
    }

    @Override
    public ServerResponse<String> delete_answer(Integer answer_id, User user) {
        Answer answer = answerDAO.findById(answer_id).get();
        if(answer==null)
            return ServerResponse.createByErrorMessage("回答不存在");
        if(answer.getUser().getId()!=user.getId()){
            return ServerResponse.createByErrorMessage("非法删除");
        }else {
            answerDAO.delete(answer);
            return ServerResponse.createBySuccessMessage("删除成功");
        }
    }

    @Override
    public ServerResponse<Answer> update_answer(Integer answer_id, User user, String answer_text) {
        Answer answer = answerDAO.findById(answer_id).get();
        if(answer==null)
            return ServerResponse.createByErrorMessage("回答不存在");
        if(answer.getUser().getId()!=user.getId()){
            return ServerResponse.createByErrorMessage("非法更新");
        }else {
            answer.setAnswer(answer_text);
            answer.setDate(new Date());
            answerDAO.save(answer);
            return ServerResponse.createBySuccess(answer);
        }
    }

    @Override
    public ServerResponse<List<Answer>> list_answerByUser(User user) {
        List<Answer> answerlist = user.getAnswerlist();
        return ServerResponse.createBySuccess(answerlist);
    }

    @Override
    public ServerResponse<List<Answer>> list_answerByQuestionId(Integer question_id) {
        Question question = questionDAO.findById(question_id).get();
        if(question==null)
            return ServerResponse.createByErrorMessage("问题不存在");
        List<Answer> answerlist = question.getAnswerlist();
        return ServerResponse.createBySuccess(answerlist);
    }

    @Override
    public ServerResponse<Answer> look_answer(Integer answer_id) {
        Answer answer = answerDAO.findById(answer_id).get();
        if(answer==null)
            return ServerResponse.createByErrorMessage("回答不存在");
        return ServerResponse.createBySuccess(answer);

    }

    @Override
    public ServerResponse<List<Answer>> push_answer(User user) {
        return null;
    }

    @Override
    public ServerResponse<List<Answer>> ManagelistAnswer(User user) {
        return null;
    }
}
