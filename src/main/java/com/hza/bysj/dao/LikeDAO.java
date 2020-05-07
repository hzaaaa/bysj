package com.hza.bysj.dao;

import com.hza.bysj.pojo.Answer;
import com.hza.bysj.pojo.Like;
import com.hza.bysj.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeDAO extends JpaRepository<Like,Integer> {

    Like findByUserAndAnswer(User user, Answer answer);


}
