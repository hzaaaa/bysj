package com.hza.bysj.service;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;

import java.util.List;

public interface IQuestionService {
    ServerResponse<Question> ask_question (String question_title,String question_explain,Tag tag, User user);
    ServerResponse<String> delete_question(Integer question_id,User user);
    ServerResponse<Question> update_question(Integer question_id,User user,String question_explain);
    ServerResponse<List<Question>> list_questionByUser(User user);
    ServerResponse<List<Question>> list_questionByTag(Tag tag);
    ServerResponse<Question> look_question(Integer question_id);
    ServerResponse<List<Question>>push_questionsByUser(User user);
    ServerResponse<List<Question>>push_questionsByDate();

    ServerResponse<List<Question>> ManagelistQuestion(User user);

}
