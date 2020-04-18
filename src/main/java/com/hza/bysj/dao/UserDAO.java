package com.hza.bysj.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hza.bysj.pojo.User;

public interface UserDAO extends JpaRepository<User,Integer>{

    int countByName(String userName);
    User findByNameAndPassword(String name,String password);
    User findByName(String name);

}