package com.lakefarm.pojo;

/**
 * Created by rxl on 17-6-6.
 */
public class Record {
    private String record_id;
    private String u_id;
    private String record_time;
    private String record_score;

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getRecord_time() {
        return record_time;
    }

    public void setRecord_time(String record_time) {
        this.record_time = record_time;
    }

    public String getRecord_score() {
        return record_score;
    }

    public void setRecord_score(String record_score) {
        this.record_score = record_score;
    }

    public String getQuestion_type() {
        return question_type;
    }

    public void setQuestion_type(String question_type) {
        this.question_type = question_type;
    }

    private String question_type;


}
