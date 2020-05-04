package com.hza.bysj.pojo;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "user_name")
    private String name;

    @Column(name = "password")
    private  String password;
    @Column(name = "password_question")
    private  String password_question;
    @Column(name = "password_answer")
    private  String password_answer;

    @ManyToMany
    @JoinTable(name = "user_tag",joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> taglist;

    @OneToMany(mappedBy = "user",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Answer> answerlist;


    public List<Answer> getAnswerlist() {
        return answerlist;
    }

    public void setAnswerlist(List<Answer> answerlist) {
        this.answerlist = answerlist;
    }

    public List<Tag> getTaglist() {
        return taglist;
    }

    public void setTaglist(List<Tag> taglist) {
        this.taglist = taglist;
    }

    public String getPassword_question() {
        return password_question;
    }

    public void setPassword_question(String password_question) {
        this.password_question = password_question;
    }

    public String getPassword_answer() {
        return password_answer;
    }

    public void setPassword_answer(String password_answer) {
        this.password_answer = password_answer;
    }

    @Column(name = "role")
    private  int role;

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public User() {
    }

    public User(String name, String password, String password_question, String password_answer, int role) {
        this.name = name;
        this.password = password;
        this.password_question = password_question;
        this.password_answer = password_answer;
        this.role = role;
    }
}
