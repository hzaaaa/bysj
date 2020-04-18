package com.hza.bysj.service;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.Answer;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.User;

import java.util.List;

public interface IAnswerService {
    ServerResponse<Answer> answer_question (Integer question_id,Integer user_id,Answer answer);
    ServerResponse<String> delete_answer(Integer question_id,User user);
    ServerResponse<Answer> update_answer(Integer answer_id,User user,Answer answer);
    ServerResponse<List<Answer>> list_answerByUser(User user);
    ServerResponse<List<Answer>> list_answerByQuestionId(Integer question_id);
    ServerResponse<Answer> look_answer(Integer answer_id);
    ServerResponse<List<Answer>>push_answer(User user);

    ServerResponse<List<Answer>> ManagelistAnswer(User user);
}
