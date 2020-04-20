package com.hza.bysj.dao;

import com.hza.bysj.pojo.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerDAO extends JpaRepository<Answer,Integer>{

    //int countByName(String userName);
    //List<Answer> findAllByOrderByDateDesc();
}