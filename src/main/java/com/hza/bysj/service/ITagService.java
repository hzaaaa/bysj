package com.hza.bysj.service;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;

import java.util.List;

public interface ITagService {
    ServerResponse<Tag> add_tag(User user, String tag);
    ServerResponse<String> delete_tag(User user, Integer tag_id);
    ServerResponse<List<Tag>> listtagByUser(User user);

    ServerResponse<List<Tag>> ManagelistTag(User user);

}
