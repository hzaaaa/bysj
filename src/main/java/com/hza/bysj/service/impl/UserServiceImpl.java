package com.hza.bysj.service.impl;

import com.hza.bysj.common.Const;
import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.dao.UserDAO;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("iUserService")
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserDAO userDAO;

    private Map<String,String> token;

    private UserServiceImpl() {
        this.token = new HashMap<String, String>();
    }

    @Override
    public ServerResponse<User> login(String username, String password) {
        System.out.println(password);
        int resultCount = userDAO.countByName(username);
        if(resultCount == 0 ){
            return ServerResponse.createByErrorMessage("用户名不存在");
        }

        User user  = userDAO.findByNameAndPassword(username,password);
        if(user == null){
            return ServerResponse.createByErrorMessage("密码错误");
        }
        if(user.getRole() == -1){
            return ServerResponse.createByErrorMessage("用户被封禁");
        }
        user.setPassword(null);
        return ServerResponse.createBySuccess("登录成功",user);
    }



    public ServerResponse<String> register(User user){
        ServerResponse validResponse = this.checkValid(user.getName(),Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        user.setRole(Const.Role.ROLE_CUSTOMER);
        userDAO.save(user);

        return ServerResponse.createBySuccessMessage("注册成功");
    }

    public ServerResponse<String> checkValid(String str,String type){
    //检查用户名是否不存在
            if(Const.USERNAME.equals(type)){
                int resultCount = userDAO.countByName(str);
                if(resultCount > 0 ){
                    return ServerResponse.createByErrorMessage("用户名已存在");
                }
            }
        return ServerResponse.createBySuccessMessage("校验成功");
    }

    public ServerResponse selectQuestion(String username){

        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        User byName = userDAO.findByName(username);
        String question = byName.getPassword_question();
        return ServerResponse.createBySuccess(question);

        //return ServerResponse.createByErrorMessage("找回密码的问题是空的");
    }

    public ServerResponse<String> checkAnswer(String username,String question,String answer){

        User result =      userDAO.findByName(username);
        if(result.getPassword_question().equals(question)&&result.getPassword_answer().equals(answer)){
            String forgetToken = UUID.randomUUID().toString();
            token.put(username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByErrorMessage("问题的答案错误");
    }


    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if(validResponse.isSuccess()){
            //用户不存在
            return ServerResponse.createByErrorMessage("用户不存在");
        }
        String token = this.token.get(username);

        if(token.equals(forgetToken)){

            User user0=userDAO.findByName(username);
            User user1=new User(username,passwordNew,user0.getPassword_question(),user0.getPassword_answer(),user0.getRole());
            user1.setId(user0.getId());
            userDAO.save(user1);

            return ServerResponse.createBySuccessMessage("修改密码成功");

        }else{
            return ServerResponse.createByErrorMessage("token错误,请重新获取重置密码的token");
        }
    }


    public ServerResponse<String> resetPassword(String passwordOld,String passwordNew,User user){
        User user0 = userDAO.findByName(user.getName());
        if(!user0.getPassword().equals( passwordOld)){
            return ServerResponse.createByErrorMessage("旧密码错误");
        }

        user.setPassword(passwordNew);
        userDAO.save(user);
        return ServerResponse.createBySuccessMessage("密码更新成功");

    }


    public ServerResponse<User> updateInformation(User user){

        User updateUser=userDAO.findById(user.getId()).get();
        updateUser.setPassword_question(user.getPassword_question());
        updateUser.setPassword_answer(user.getPassword_answer());
        updateUser.setRole(user.getRole());

        userDAO.save(updateUser);
        return ServerResponse.createBySuccess("更新个人信息成功",updateUser);

    }



    public ServerResponse<User> getInformation(Integer userId){
        Optional<User> user = userDAO.findById(userId);
        if(user == null){
            return ServerResponse.createByErrorMessage("找不到当前用户");
        }
        user.get().setPassword(null);
        return ServerResponse.createBySuccess(user.get());

    }




    //backend

    /**
     * 校验是否是管理员
     */
    public ServerResponse checkAdminRole(User user){
        if(user != null && user.getRole() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }

    @Override
    public ServerResponse<Page<User>> userList(Integer page, Integer size) {

        page=page<0?0:page;
        PageRequest of = PageRequest.of(page, size);
        Page<User> all = userDAO.findAll(of);

        Iterator<User> iterator = all.iterator();
        while(iterator.hasNext()){
            User next = iterator.next();
            next.setPassword(null);
            next.setPassword_answer(null);
        }


        return ServerResponse.createBySuccess(all);
    }

    @Override
    public ServerResponse<String> ManageDeleteUser(Integer id) {
        Optional<User> byId = userDAO.findById(id);
        if(byId == null)return ServerResponse.createByErrorMessage("用户不存在");
        User user = byId.get();
        user.setRole(-1);
        userDAO.save(user);

        return ServerResponse.createBySuccessMessage("用户封禁成功");
    }


}
