package com.lakefarm.service;

import com.lakefarm.pojo.Question;

import java.util.List;

/**
 * Created by rxl on 17-2-24.
 */
public interface QuestionService {
    /*
   *增加测试题目
   */
    public int addQuestion(Question t);


    /*
    *删除测试题目
    */
    public int deleteQuestion(String question_id);

    /*
    *更新测试题目
    */
    public int updateQuestion(Question t);

    /*
    *通过类型获取该类型全部测试题目
    */
    public List<Question> getQuestionByQuestionType(String Question_type);

    /*
    *通过id获取指定测试题目
    */
    public Question getQuestionById(String question_id);

    /*
    *随机获取测试题目
    */
    public List<Question> getRandomQuestionById();

    public List<Question> getAllQuestion();
}
