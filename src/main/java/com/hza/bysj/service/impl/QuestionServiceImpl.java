package com.hza.bysj.service.impl;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.dao.QuestionDAO;
import com.hza.bysj.dao.UserDAO;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("iQuestion")
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private UserDAO userDAO;


    private Comparator<Question> c = new Comparator<Question>() {
        @Override
        public int compare(Question q1, Question q2) {
            return q1.getDate().compareTo(q2.getDate());
        }
    };
    @Override
    public ServerResponse<Question> ask_question(String question_title,String question_explain,Tag tag, User user) {

        Question question1 = new Question();
        question1.setUser(user);
        question1.setDate(new Date());
        question1.setQuestion_title(question_title);
        question1.setQuestion_explain(question_explain);
        question1.setTag(tag);
        Question save = questionDAO.save(question1);

        user.getQuestionlist().add(save);
        Optional<User> byId = userDAO.findById(user.getId());
        byId.get().setTaglist(user.getTaglist());
        byId.get().setQuestionlist(user.getQuestionlist());

        userDAO.save(byId.get());
        return ServerResponse.createBySuccess("提问成功",save);
    }

    @Override
    public ServerResponse<String> delete_question(Integer question_id, User user) {

        Optional<Question> byId = questionDAO.findById(question_id);
        Question question = byId.get();
        if(question==null)return ServerResponse.createByErrorMessage("该问题不存在");
        if(question.getUser().getId()==user.getId()){
            questionDAO.delete(question);
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("该用户无权删除");
    }

    @Override
    public ServerResponse<Question> update_question(Integer question_id, User user, String question_explain) {

        Optional<Question> byId = questionDAO.findById(question_id);
        Question question1 = byId.get();
        if(question1==null)return ServerResponse.createByErrorMessage("该问题不存在");
        if(question1.getUser().getId()==user.getId()){
            question1.setQuestion_explain(question_explain);
            Question save = questionDAO.save(question1);
            return ServerResponse.createBySuccess("更新成功",save);
        }
        return ServerResponse.createByErrorMessage("该用户无权更新");

    }


    @Override
    public ServerResponse<List<Question>> list_questionByUser(User user) {

        List<Question> questions = questionDAO.findAllByUser_id(user.getId());
        return ServerResponse.createBySuccess(questions);

    }

    @Override
    public ServerResponse<List<Question>> list_questionByTag(Tag tag) {

        List<Question> questions = questionDAO.findAllByTag_id(tag.getId());
        return ServerResponse.createBySuccess(questions);
    }

    @Override
    public ServerResponse<Question> look_question(Integer question_id) {
        Optional<Question> byId = questionDAO.findById(question_id);
        Question question = byId.get();
        if(question==null)return ServerResponse.createByErrorMessage("无此问题");
        return ServerResponse.createBySuccess(question);

    }

    @Override
    public ServerResponse<List<Question>> push_questionsByUser(User user) {

        LinkedList<Question> questionslist = new LinkedList<>();
        List<Tag> taglist = user.getTaglist();
        Iterator<Tag> taglistiterator = taglist.iterator();
        for(int i=0;i<taglist.size();i++){
            List<Question> questions = questionDAO.findAllByTag_id(taglistiterator.next().getId());
            Iterator<Question> iterator = questions.iterator();
            for(int j=0;j<questions.size();j++){
                questionslist.add(iterator.next());
            }
        }

        questionslist.sort(c);

        return ServerResponse.createBySuccess(questionslist);
    }

    @Override
    public ServerResponse<List<Question>> push_questionsByDate() {
        List<Question> questions = questionDAO.findAllByOrderByDateDesc();
        return ServerResponse.createBySuccess(questions);
    }

    @Override
    public ServerResponse<List<Question>> ManagelistQuestion(User user) {
        List<Question> questions = questionDAO.findAllByOrderByDateDesc();
        return ServerResponse.createBySuccess(questions);
    }

    @Override
    public ServerResponse<List<Question>> searchQuestion(String question) {
        return null;
    }
}
