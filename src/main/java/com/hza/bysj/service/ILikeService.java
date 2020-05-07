package com.hza.bysj.service;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.User;

public interface ILikeService {

    ServerResponse<String>   like(User user,Integer answer_id);
    ServerResponse<String>   dislike(User user,Integer answer_id);

}
