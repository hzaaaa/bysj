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

    @JsonIgnore
    @OneToMany(cascade={CascadeType.REMOVE},mappedBy="question")
    private List<Answer> answerList;

    @JsonIgnore
    @OneToMany(cascade={CascadeType.REMOVE},mappedBy="question")
    private List<Invite> inviteList;

    public List<Invite> getInviteList() {
        return inviteList;
    }

    public void setInviteList(List<Invite> inviteList) {
        this.inviteList = inviteList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Answer> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(List<Answer> answerList) {
        this.answerList = answerList;
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
