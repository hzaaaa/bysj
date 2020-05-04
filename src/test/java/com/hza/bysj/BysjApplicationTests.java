package com.hza.bysj;

import com.hza.bysj.common.CodeCache;
import com.hza.bysj.common.util;
import com.hza.bysj.dao.TagDAO;
import com.hza.bysj.dao.UserDAO;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;
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
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.util.ArrayList;

@SpringBootTest
class BysjApplicationTests {

    @Autowired
    TagDAO tagDAO;
    @Autowired
    UserDAO userDAO;

    @Autowired
    CodeCache codeCache;
    //@Test
    void savetag() {
        Tag tag = new Tag();
        tag.setId(1);
        tag.setTag("计算机");
        tagDAO.save(tag);
    }
    //@Test
    void saveuser(){
        ArrayList<Tag> objects = new ArrayList<>();
        objects.add(tagDAO.findById(1).get());
        User user = new User();
        user.setTaglist(objects);
        userDAO.save(user);
    }
    @Test
    void testsearcher() throws Exception {

    }
}
