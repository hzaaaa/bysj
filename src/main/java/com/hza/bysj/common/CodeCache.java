package com.hza.bysj.common;

import com.hza.bysj.dao.QuestionDAO;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.service.IQuestionService;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;


@Component("codeCache")
public class CodeCache {


    public static Directory index = new RAMDirectory();
    public static IKAnalyzer analyzer = new IKAnalyzer();

    @Autowired
    private QuestionDAO questionDAO;

    @PostConstruct
    public void init() {
        System.out.println("test init");
        List<Question> questionList = questionDAO.findAll();
        try {
            util.createIndex(index,analyzer,questionList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
