package com.hza.bysj.service.impl;

import com.hza.bysj.common.CodeCache;
import com.hza.bysj.common.ServerResponse;
import com.hza.bysj.common.util;
import com.hza.bysj.dao.QuestionDAO;
import com.hza.bysj.dao.TagDAO;
import com.hza.bysj.dao.UserDAO;
import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.Tag;
import com.hza.bysj.pojo.User;
import com.hza.bysj.service.IQuestionService;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service("iQuestion")
public class QuestionServiceImpl implements IQuestionService {

    @Autowired
    private QuestionDAO questionDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TagDAO tagDAO;


    private Comparator<Question> c = new Comparator<Question>() {
        @Override
        public int compare(Question q1, Question q2) {
            return q1.getDate().compareTo(q2.getDate());
        }
    };
    @Override
    public ServerResponse<Question> ask_question(String question_title,String question_explain,Tag tag, User user) {

        Question question1 = new Question();
        question1.setUser(user);
        question1.setDate(new Date());
        question1.setQuestion_title(question_title);
        question1.setQuestion_explain(question_explain);
        question1.setTag(tag);
        Question save = questionDAO.save(question1);

        IndexWriterConfig config = new IndexWriterConfig(CodeCache.analyzer);
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(CodeCache.index, config);

        } catch (IOException e) {
            e.printStackTrace();
        }
        util.addDoc(writer,save);
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ServerResponse.createBySuccess("提问成功",save);
    }

    @Override
    public ServerResponse<String> delete_question(Integer question_id, User user) {

        Optional<Question> byId = questionDAO.findById(question_id);

        if(byId == null)return ServerResponse.createByErrorMessage("该问题不存在");
        Question question = byId.get();
        if(question.getUser().getId()==user.getId()){

            questionDAO.delete(question);

            IndexWriterConfig config = new IndexWriterConfig(CodeCache.analyzer);
            IndexWriter indexWriter = null;
            try {
                indexWriter = new IndexWriter(CodeCache.index, config);
                indexWriter.deleteDocuments(new Term("id", question.getId().toString()));
                indexWriter.commit();
                indexWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return ServerResponse.createBySuccessMessage("删除成功");
        }
        return ServerResponse.createByErrorMessage("该用户无权删除");
    }

    @Override
    public ServerResponse<Question> update_question(Integer question_id, User user, String question_explain) {

        Optional<Question> byId = questionDAO.findById(question_id);
        if(byId==null)return ServerResponse.createByErrorMessage("该问题不存在");
        Question question1 = byId.get();
        if(question1.getUser().getId()==user.getId()){
            question1.setQuestion_explain(question_explain);
            Question save = questionDAO.save(question1);

            IndexWriterConfig config = new IndexWriterConfig(CodeCache.analyzer);
            IndexWriter indexWriter = null;
            try {
                indexWriter = new IndexWriter(CodeCache.index, config);
                Document doc = new Document();
                doc.add(new TextField("id",save.getId().toString(), Field.Store.YES));
                doc.add(new TextField("question_title", save.getQuestion_title(), Field.Store.YES));
                doc.add(new TextField("question_explain", save.getQuestion_explain(), Field.Store.YES));

                indexWriter.updateDocument(new Term("id", save.getId().toString()), doc );
                indexWriter.commit();
                indexWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return ServerResponse.createBySuccess("更新成功",save);
        }
        return ServerResponse.createByErrorMessage("该用户无权更新");

    }


    @Override
    public ServerResponse<List<Question>> list_questionByUser(User user) {

        List<Question> questions = questionDAO.findAllByUser_id(user.getId());
        return ServerResponse.createBySuccess(questions);

    }

    @Override
    public ServerResponse<List<Question>> list_questionByTag(Tag tag) {

        List<Question> questions = questionDAO.findAllByTag_id(tag.getId());
        return ServerResponse.createBySuccess(questions);
    }

    @Override
    public ServerResponse<Question> look_question(Integer question_id) {
        Optional<Question> byId = questionDAO.findById(question_id);
        if(byId==null)return ServerResponse.createByErrorMessage("无此问题");
        Question question = byId.get();
        return ServerResponse.createBySuccess(question);

    }

    @Override
    public ServerResponse<Page<Question>> pull_questionsByUser(User user,Integer page,Integer size) {

        User save = userDAO.findById(user.getId()).get();
        LinkedList<Question> questionslist = new LinkedList<>();
        List<Tag> taglist = save.getTaglist();
        Iterator<Tag> taglistiterator = taglist.iterator();
        for(int i=0;i<taglist.size();i++){
            List<Question> questions = questionDAO.findAllByTag_id(taglistiterator.next().getId());
            Iterator<Question> iterator = questions.iterator();
            for(int j=0;j<questions.size();j++){
                questionslist.add(iterator.next());
            }
        }

        questionslist.sort(c);
        page=page<0?0:page;
        PageRequest of = PageRequest.of(page, size);
        Page<Question> questions = util.listConvertToPage(questionslist, of);

        return ServerResponse.createBySuccess(questions);
    }

    @Override
    public ServerResponse<Page<Question>> pull_questionsByDate(Integer page ,Integer size) {
        int start = page;
        start = start<0?0:start;
        Sort sort =  Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(start, size, sort);
        Page<Question> page0 =questionDAO.findAll(pageable);

        return ServerResponse.createBySuccess(page0);
    }

    @Override
    public ServerResponse<List<Question>> ManagelistQuestion(User user) {
        List<Question> questions = questionDAO.findAllByOrderByDateDesc();
        return ServerResponse.createBySuccess(questions);
    }


}
