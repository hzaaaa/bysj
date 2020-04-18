package com.hza.bysj.web;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.hza.bysj.dao.UserDAO;
import com.hza.bysj.pojo.User;

//@Controller
public class UserController {
    @Autowired
    UserDAO UserDAO;

    @RequestMapping("/listUser")
    public String listUser(Model m,@RequestParam(value = "start", defaultValue = "0") int start,@RequestParam(value = "size", defaultValue = "5") int size) throws Exception {
        start = start<0?0:start;
        Sort sort =  Sort.by(Sort.Direction.ASC, "id");
        Pageable pageable =  PageRequest.of(start, size, sort);
        Page<User> page =UserDAO.findAll(pageable);

        /*System.out.println(page.getNumber());
        System.out.println(page.getNumberOfElements());
        System.out.println(page.getSize());
        System.out.println(page.getTotalElements());
        System.out.println(page.getTotalPages());*/

        m.addAttribute("page", page);
        return "listUser";
    }

    @RequestMapping("/addUser")
    public String addUser(User c) throws Exception {
        UserDAO.save(c);
        return "redirect:listUser";
    }
    @RequestMapping("/deleteUser")
    public String deleteUser(User c) throws Exception {
        UserDAO.delete(c);
        return "redirect:listUser";
    }
    @RequestMapping("/updateUser")
    public String updateUser(User c) throws Exception {
        UserDAO.save(c);
        return "redirect:listUser";
    }
    @RequestMapping("/editUser")
    public String editUser(int id,Model m) throws Exception {
        User c= UserDAO.getOne(id);
        m.addAttribute("c", c);
        return "editUser";
    }

}