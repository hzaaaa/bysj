package com.hza.bysj.web;

import com.hza.bysj.dao.UserDAO;
import com.hza.bysj.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

@Autowired UserDAO userDAO;
    @RequestMapping("/hello")
    public String hello() {

        if(userDAO.findByName("user0")==null)
        return   "null";
        return "yes";

    }

}