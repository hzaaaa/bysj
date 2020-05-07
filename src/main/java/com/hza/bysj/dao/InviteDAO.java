package com.hza.bysj.dao;

import com.hza.bysj.pojo.Invite;
import com.hza.bysj.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InviteDAO extends JpaRepository<Invite,Integer> {

    List<Invite> findAllByInviteeOrderByDateDesc(User invitee);
}
