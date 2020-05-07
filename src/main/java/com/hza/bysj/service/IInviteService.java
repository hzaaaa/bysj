package com.hza.bysj.service;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.Invite;
import com.hza.bysj.pojo.User;

import java.util.List;

public interface IInviteService {

    ServerResponse<Invite> invite_answer(User inviter,String invitee,Integer question_id);
    ServerResponse<List<Invite>> look_myInvitedList(User user);
}
