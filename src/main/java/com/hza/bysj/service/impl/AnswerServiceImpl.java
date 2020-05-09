package com.hza.bysj.service.impl;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.dao.AnswerDAO;
import com.hza.bysj.dao.QuestionDAO;
import com.hza.bysj.pojo.Answer;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("iAnswerService")
public class AnswerServiceImpl implements IAnswerService {

    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private AnswerDAO answerDAO;

    private Comparator<Answer> c = new Comparator<Answer>() {
        @Override
        public int compare(Answer a1, Answer a2) {
            if(a1.getLove()-a1.getBoring()>a2.getLove()-a2.getBoring ())
                return 1;
            else return -1;
        }
    };

    @Override
    public ServerResponse<Answer> answer_question(Integer question_id, User user, String answer) {
        Question question = questionDAO.findById(question_id).get();
        if(question==null)
            return ServerResponse.createByErrorMessage("问题不存在");
        Answer answer1 = new Answer();
        answer1.setAnswer(answer);
        answer1.setDate(new Date());
        answer1.setBoring(0);
        answer1.setLove (0);
        answer1.setQuestion(question);
        answer1.setUser(user);
        Answer save = answerDAO.save(answer1);
        return ServerResponse.createBySuccess(save);
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
        List<Answer> answerlist = answerDAO.findByUser(user);
        removePassword(answerlist.iterator());
        return ServerResponse.createBySuccess(answerlist);
    }

    @Override
    public ServerResponse<List<Answer>> list_answerByQuestionId(Integer question_id) {
        Question question = questionDAO.findById(question_id).get();
        if(question==null)
            return ServerResponse.createByErrorMessage("问题不存在");
        List<Answer> answerlist = answerDAO.findByQuestion(question);
        Iterator<Answer> iterator = answerlist.iterator();

        removePassword(iterator);

        return ServerResponse.createBySuccess(answerlist);
    }

    @Override
    public ServerResponse<Answer> look_answer(Integer answer_id) {
        Answer answer = answerDAO.findById(answer_id).get();
        if(answer==null)
            return ServerResponse.createByErrorMessage("回答不存在");
        User user = new User();
        user.setName(answer.getUser().getName());
        answer.setUser(user);
        return ServerResponse.createBySuccess(answer);

    }

    @Override
    public ServerResponse<Page<Answer>> push_answer(Integer page,Integer size) {
        page = page<0?0:page;
        Sort sort =  Sort.by(Sort.Direction.DESC, "love");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Answer> all = answerDAO.findAll(pageable);

        Iterator<Answer> iterator = all.iterator();

        removePassword(iterator);


        return ServerResponse.createBySuccess(all);
    }

    private void removePassword(Iterator<Answer> iterator) {
        while (iterator.hasNext()) {
            Answer next = iterator.next();
            User user = new User();
            user.setName(next.getUser().getName());
            next.setUser(user);
        }
    }

    @Override
    public ServerResponse<List<Answer>> ManagelistAnswer(User user) {
        List<Answer> answerlist = answerDAO.findAll();
        return ServerResponse.createBySuccess(answerlist);
    }

}
