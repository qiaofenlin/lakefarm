package com.lakefarm.service;

import com.lakefarm.pojo.PersonQuestion;

/**
 * Created by rxl on 17-2-24.
 */
public interface PersonQuestionService {
    /*
   *增加个人测试结果
   */
    public int addPersonQuestion(PersonQuestion t);

    /*
    *删除个人测试结果
    */
    public int deletePersonQuestion(String uq_id);

    /*
    *更新个人测试结果
    */
    public int updatePersonQuestion(PersonQuestion t);

    /*
    *通过id获取指定个人测试结果
    */
    public PersonQuestion getPersonQuestionById(String u_id,String question_id);
}
