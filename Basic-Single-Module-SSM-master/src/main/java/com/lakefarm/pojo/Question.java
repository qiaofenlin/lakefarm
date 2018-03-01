package com.lakefarm.pojo;

/**
 * Created by rxl on 17-2-24.
 */
public class Question {
    private String question_id;
    private String question_title;
    private String question_type;
    private String question_selection;
    private String question_answer;

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_title() {
        return question_title;
    }

    public void setQuestion_title(String question_title) {
        this.question_title = question_title;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    public String getQuestion_selection() {
        return question_selection;
    }

    public void setQuestion_selection(String question_selection) {
        this.question_selection = question_selection;
    }

    public String getQuestion_answer() {
        return question_answer;
    }

    public void setQuestion_answer(String question_answer) {
        this.question_answer = question_answer;
    }
}
