package com.hza.bysj.dao;

import com.hza.bysj.pojo.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionDAO extends JpaRepository<Question,Integer>{

    List<Question> findAllByUser_id(Integer user_id);
    List<Question> findAllByTag_id(Integer tag_id);
    List<Question> findAllByOrderByDateDesc();

    //int countByName(String userName);
}