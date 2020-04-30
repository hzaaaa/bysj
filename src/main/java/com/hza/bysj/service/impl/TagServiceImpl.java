package com.hza.bysj.service.impl;

import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.dao.TagDAO;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service("iTagService")
public class TagServiceImpl implements ITagService {
    @Autowired
    TagDAO tagDAO;
    @Override
    public ServerResponse<Tag> add_tag(User user, String tag) {
        Tag newtag = tagDAO.findByTag(tag);
        if(newtag==null){
            Tag tag1 = new Tag();
            tag1.setTag(tag);
            newtag=tagDAO.save(tag1);
            user.getTaglist().add(newtag);
        }else{
            Iterator<Tag> iterator = user.getTaglist().iterator();
            while (iterator.hasNext()){
                if(iterator.next().getId()==newtag.getId()){
                    return ServerResponse.createBySuccess(newtag);
                }
            }
            user.getTaglist().add(newtag);
        }
        return ServerResponse.createBySuccess(newtag);
    }

    @Override
    public ServerResponse<String> delete_tag(User user, Integer tag_id) {
        Tag tag = tagDAO.findById(tag_id).get();
        if(tag==null)
            return ServerResponse.createByErrorMessage("tag不存在");
        tagDAO.delete(tag);
        return ServerResponse.createBySuccessMessage("删除成功");
    }

    @Override
    public ServerResponse<List<Tag>> listtagByUser(User user) {
        return ServerResponse.createBySuccess(user.getTaglist());
    }

    @Override
    public ServerResponse<List<Tag>> ManagelistTag(User user) {
        return ServerResponse.createBySuccess(tagDAO.findAll());
    }
}
