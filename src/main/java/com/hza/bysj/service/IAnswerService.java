package com.hza.bysj.service;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.Answer;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IAnswerService {
    ServerResponse<Answer> answer_question (Integer question_id,User user,String answer);
    ServerResponse<String> delete_answer(Integer answer_id,User user);
    ServerResponse<Answer> update_answer(Integer answer_id,User user,String answer_text);
    ServerResponse<List<Answer>> list_answerByUser(User user);
    ServerResponse<List<Answer>> list_answerByQuestionId(Integer question_id);
    ServerResponse<Answer> look_answer(Integer answer_id);
    ServerResponse<Page<Answer>>push_answer(Integer page, Integer size);

    ServerResponse<Page<Answer>> ManagelistAnswer(Integer page, Integer size);
    ServerResponse<String> ManagedeleteAnswer(Integer id);
}
