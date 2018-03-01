package com.lakefarm.service.Impl;

import com.lakefarm.mapper.QuestionMapper;
import com.lakefarm.pojo.Question;
import com.lakefarm.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rxl on 17-2-24.
 */
@Service
public class QuestionServiceImp implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Override
    public int addQuestion(Question t) {
        return questionMapper.addQuestion(t);
    }

    @Override
    public int deleteQuestion(String question_id) {
        return questionMapper.deleteQuestion(question_id);
    }

    @Override
    public int updateQuestion(Question t) {
        return questionMapper.updateQuestion(t);
    }

    @Override
    public List<Question> getQuestionByQuestionType(String Question_type) {
        return questionMapper.getQuestionByQuestionType(Question_type);
    }

    @Override
    public Question getQuestionById(String question_id) {
        return questionMapper.getQuestionById(question_id);
    }

    @Override
    public List<Question> getRandomQuestionById() {
        return questionMapper.getRandomQuestionById();
    }

    @Override
    public List<Question> getAllQuestion() {
        return questionMapper.getAllQuestion();
    }
}
