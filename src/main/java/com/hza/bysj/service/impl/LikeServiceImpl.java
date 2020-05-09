package com.hza.bysj.service.impl;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.dao.AnswerDAO;
import com.hza.bysj.dao.LikeDAO;
import com.hza.bysj.pojo.Answer;
import com.hza.bysj.pojo.Like;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("iLikeService")
public class LikeServiceImpl implements ILikeService {

    @Autowired
    private AnswerDAO answerDAO;

    @Autowired
    private LikeDAO likeDAO;

    @Override
    public ServerResponse<String> like(User user, Integer answer_id) {

        Optional<Answer> byId = answerDAO.findById(answer_id);
        if(byId == null){
            return ServerResponse.createByErrorMessage("回答不存在");
        }
        Answer answer = byId.get();
        Like byUserAndAnswer = likeDAO.findByUserAndAnswer(user, answer);
        if(byUserAndAnswer == null){

            Like like = new Like();
            like.setAnswer(answer);
            like.setUser(user);
            like.setIslike(1);
            likeDAO.save(like);

            answer.setLove (answer.getLove ()+1);
            answerDAO.save(answer);
        }else {
            if(byUserAndAnswer.getIslike() == -1){
                byUserAndAnswer.setIslike(1);
                likeDAO.save(byUserAndAnswer);

                answer.setLove (answer.getLove ()+1);
                answer.setBoring (answer.getBoring ()-1);
                answerDAO.save(answer);
            }else{
                return ServerResponse.createBySuccess("已点赞");
            }

        }

        return ServerResponse.createBySuccess("点赞成功");
    }

    @Override
    public ServerResponse<String> dislike(User user, Integer answer_id) {

        Optional<Answer> byId = answerDAO.findById(answer_id);
        if(byId == null){
            return ServerResponse.createByErrorMessage("回答不存在");
        }
        Answer answer = byId.get();
        Like byUserAndAnswer = likeDAO.findByUserAndAnswer(user, answer);
        if(byUserAndAnswer == null){

            Like like = new Like();
            like.setAnswer(answer);
            like.setUser(user);
            like.setIslike(-1);
            likeDAO.save(like);

            answer.setBoring (answer.getBoring ()+1);
            answerDAO.save(answer);
        }else {
            if(byUserAndAnswer.getIslike() == 1){
                byUserAndAnswer.setIslike(-1);
                likeDAO.save(byUserAndAnswer);

                answer.setLove (answer.getLove ()-1);
                answer.setBoring (answer.getBoring ()+1);
                answerDAO.save(answer);
            }else{
                return ServerResponse.createBySuccess("已点踩");
            }

        }

        return ServerResponse.createBySuccess("点踩成功");
    }
}
