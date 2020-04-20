package com.hza.bysj;

import com.hza.bysj.dao.TagDAO;
import com.hza.bysj.dao.UserDAO;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class BysjApplicationTests {

    @Autowired
    TagDAO tagDAO;
    @Autowired
    UserDAO userDAO;
    @Test
    void savetag() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setTag("计算机");
        tagDAO.save(tag);
    }
    @Test
    void saveuser(){
        ArrayList<Tag> objects = new ArrayList<>();
        objects.add(tagDAO.findById(1).get());
        User user = new User();
        user.setTaglist(objects);
        userDAO.save(user);
    }

}
