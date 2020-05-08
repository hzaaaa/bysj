package com.hza.bysj.service.impl;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.dao.InviteDAO;
import com.hza.bysj.dao.QuestionDAO;
import com.hza.bysj.dao.UserDAO;
import com.hza.bysj.pojo.Invite;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service("iInviteService")
public class InviyeServiceImpl implements IInviteService {

    @Autowired
    UserDAO userDAO;
    @Autowired
    InviteDAO inviteDAO;
    @Autowired
    QuestionDAO questionDAO;

    @Override
    public ServerResponse<Invite> invite_answer(User inviter, String invitee, Integer question_id) {
        int countByName = userDAO.countByName(invitee);
        if(countByName == 0)return ServerResponse.createByErrorMessage("被邀请人不存在");
        User byName = userDAO.findByName(invitee);

        Optional<Question> byId = questionDAO.findById(question_id);
        if(byId == null)return ServerResponse.createByErrorMessage("问题不存在");
        Question question = byId.get();

        Invite invite = new Invite();
        invite.setInviter(inviter);
        invite.setInvitee(byName);
        invite.setQuestion(question);
        invite.setDate(new Date());
        Invite save = inviteDAO.save(invite);

        return ServerResponse.createBySuccess(save);
    }

    @Override
    public ServerResponse<List<Invite>> look_myInvitedList(User user) {
        List<Invite> invitedList = inviteDAO.findAllByInviteeOrderByDateDesc(user);
        return ServerResponse.createBySuccess(invitedList);
    }
}