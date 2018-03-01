package com.lakefarm.service.Impl;

import com.lakefarm.mapper.PersonQuestionMapper;
import com.lakefarm.pojo.PersonGrowPlants;
import com.lakefarm.pojo.PersonQuestion;
import com.lakefarm.service.PersonGrowPlantsService;
import com.lakefarm.service.PersonQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rxl on 17-2-24.
 */
@Service
public class PersonQuestionServiceImp implements PersonQuestionService{
    @Autowired
    private PersonQuestionMapper personQuestionMapper;

    @Override
    public int addPersonQuestion(PersonQuestion t) {
        return personQuestionMapper.addPersonQuestion(t);
    }

    @Override
    public int deletePersonQuestion(String uq_id) {
        return personQuestionMapper.deletePersonQuestion(uq_id);
    }

    @Override
    public int updatePersonQuestion(PersonQuestion t) {
        return personQuestionMapper.updatePersonQuestion(t);
    }

    @Override
    public PersonQuestion getPersonQuestionById(String u_id, String question_id) {
        return personQuestionMapper.getPersonQuestionById(u_id,question_id);
    }
}
