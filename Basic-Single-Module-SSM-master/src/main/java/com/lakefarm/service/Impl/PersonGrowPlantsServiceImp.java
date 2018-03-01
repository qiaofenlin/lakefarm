package com.lakefarm.service.Impl;

import com.lakefarm.mapper.PersonGrowPlantsMapper;
import com.lakefarm.pojo.PersonGrowPlants;
import com.lakefarm.service.PersonGrowPlantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rxl on 17-2-23.
 */
@Service
public class PersonGrowPlantsServiceImp implements PersonGrowPlantsService {
    @Autowired
    private PersonGrowPlantsMapper personGrowPlantsMapper;
    @Override
    public int addPersonGrow(PersonGrowPlants t) {
        return personGrowPlantsMapper.addPersonGrow(t);
    }

    @Override
    public int deletePersonGrow(String uzz_id) {
        return personGrowPlantsMapper.deletePersonGrow(uzz_id);
    }

    @Override
    public int updatePersonGrow(PersonGrowPlants t) {
        return personGrowPlantsMapper.updatePersonGrow(t);
    }

    @Override
    public PersonGrowPlants getPersonGrowById(String uzz_id) {
        return personGrowPlantsMapper.getPersonGrowById(uzz_id);
    }

    @Override
    public List<PersonGrowPlants> getPersonGrowByUid(String u_id) {
        return personGrowPlantsMapper.getPersonGrowByUid(u_id);
    }

    @Override
    public PersonGrowPlants getPersonGrowByUzzid(String u_id, String uzz_id) {
        return personGrowPlantsMapper.getPersonGrowByUzzid(u_id,uzz_id);
    }
}
