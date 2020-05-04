package com.hza.bysj.common;

import com.hza.bysj.pojo.Question;
import com.hza.bysj.pojo.User;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.util.Version;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexableField;

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
import java.util.List;

public class util {

    public static <T> Page<T> listConvertToPage(List<T> list, Pageable pageable) {
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (start + pageable.getPageSize());
        return new PageImpl<T>(list.subList(start, end), pageable, list.size());
    }

    public static Directory createIndex(Directory index, IKAnalyzer analyzer, List<Question> products) throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(index, config);

        for (Question question : products) {
            addDoc(writer, question);
        }
        writer.close();
        return index;
    }
    public static void addDoc(IndexWriter w, Question question)  {
        Document doc = new Document();
        System.out.println(question.getQuestion_explain());
        doc.add(new TextField("id", question.getId().toString(), Field.Store.YES));
        doc.add(new TextField("question_title", question.getQuestion_title(), Field.Store.YES));
        doc.add(new TextField("question_explain", question.getQuestion_explain(), Field.Store.YES));
        try {
            w.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ServerResponse<List<Question>> search(String keyword)  {
        Query query = null;
        try {
            query = new QueryParser("question_explain", CodeCache.analyzer).parse(keyword);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        IndexReader reader = null;
        try {
            reader = DirectoryReader.open(CodeCache.index);
        } catch (IOException e) {
            e.printStackTrace();
        }
        IndexSearcher searcher = new IndexSearcher(reader);
        int numberPerPage = 1000;
        System.out.printf("查询关键字是：\"%s\"%n",keyword);
        ScoreDoc[] hits = new ScoreDoc[0];
        try {
            hits = searcher.search(query, numberPerPage).scoreDocs;
        } catch (IOException e) {
            e.printStackTrace();
        }

        ServerResponse<List<Question>> listServerResponse = showSearchResults(searcher, hits, query, CodeCache.analyzer);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listServerResponse;

    }
    private static ServerResponse<List<Question>> showSearchResults(IndexSearcher searcher, ScoreDoc[] hits, Query query, IKAnalyzer analyzer)
             {
        System.out.println("找到 " + hits.length + " 个命中.");
        System.out.println("序号\t匹配度得分\t结果");

        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < hits.length; ++i) {
            ScoreDoc scoreDoc= hits[i];
            int docId = scoreDoc.doc;
            Document d = null;
            try {
                d = searcher.doc(docId);
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<IndexableField> fields = d.getFields();


            System.out.print((i + 1));
            Question question = new Question();
            question.setQuestion_title(d.get("question_title"));
            question.setQuestion_explain(d.get("question_explain"));
            questions.add(question);


        }
        return ServerResponse.createBySuccess(questions);
    }

}
