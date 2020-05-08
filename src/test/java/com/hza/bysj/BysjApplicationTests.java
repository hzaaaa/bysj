package com.hza.bysj;

import com.hza.bysj.common.CodeCache;
import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.common.util;
import com.hza.bysj.dao.AnswerDAO;
import com.hza.bysj.dao.TagDAO;
import com.hza.bysj.dao.UserDAO;
import com.hza.bysj.pojo.Answer;
import com.hza.bysj.pojo.Invite;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IInviteService;
import com.hza.bysj.service.ILikeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.stereotype.Service;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BysjApplicationTests {

    @Autowired
    TagDAO tagDAO;
    @Autowired
    UserDAO userDAO;

    @Autowired
    AnswerDAO answerDAO;

    @Autowired
    private ILikeService iLikeService;

    @Autowired
    private IInviteService iInviteService;

    @Autowired
    CodeCache codeCache;

    @Test
    void testsearcher() throws Exception {
        User user = new User();
        user.setId(1);
        String invitee = "test";
        Integer question_id = 10;

        iInviteService.invite_answer(user,invitee,question_id);

        user.setId(2);
        ServerResponse<List<Invite>> listServerResponse = iInviteService.look_myInvitedList(user);
        System.out.println(listServerResponse.getData().remove(0).getInviter().getName());
    }
    @Test
    void testlike(){
        User user = new User();
        user.setId(1);
        iLikeService.like(user,2);
    }
}
