package com.hza.bysj.dao;

import com.hza.bysj.pojo.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagDAO extends JpaRepository<Tag,Integer>{

    //int countByName(String userName);
}