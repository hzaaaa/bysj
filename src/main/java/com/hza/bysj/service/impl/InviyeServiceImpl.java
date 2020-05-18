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
import java.util.Iterator;
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
    public ServerResponse<String> invite_answer(User inviter, String invitee, Integer question_id) {
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
        invite.setDate(new java.sql.Date(new java.util.Date().getTime()));
        Invite save = inviteDAO.save(invite);

        return ServerResponse.createBySuccessMessage("邀请成功");
    }

    @Override
    public ServerResponse<List<Invite>> look_myInvitedList(User user) {
        List<Invite> invitedList = inviteDAO.findAllByInviteeOrderByDateDesc(user);
        Iterator<Invite> iterator = invitedList.iterator();

        while (iterator.hasNext()){
            User user1 = new User();
            Invite next = iterator.next();
            user1.setName(next.getInviter().getName());
            next.setInviter(user1);
            next.setInvitee(null);
        }
        return ServerResponse.createBySuccess(invitedList);
    }

    @Override
    public ServerResponse<String> deleteInviteById(Integer id,User user) {
        Optional<Invite> byId = inviteDAO.findById(id);
        if(byId == null){
            return ServerResponse.createByErrorMessage("invite不存在");
        }
        Invite invite = byId.get();
        if(invite.getInvitee().getId()!=user.getId()){
            return ServerResponse.createByErrorMessage("无权删除invite");
        }else{
            inviteDAO.delete(invite);
            return ServerResponse.createBySuccessMessage("删除成功");
        }

    }
}
