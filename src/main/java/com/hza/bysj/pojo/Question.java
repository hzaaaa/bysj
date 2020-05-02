package com.hza.bysj.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Column(name = "date")
    private Date date;
    @Column(name = "question_title")
    private String question_title;
    @Column(name = "question_explain")
    private String question_explain;

    @OneToMany(mappedBy = "question",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Answer> answerlist;

    public List<Answer> getAnswerlist() {
        return answerlist;
    }

    public void setAnswerlist(List<Answer> answerlist) {
        this.answerlist = answerlist;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_explain() {
        return question_explain;
    }

    public void setQuestion_explain(String question_explain) {
        this.question_explain = question_explain;
    }
}
