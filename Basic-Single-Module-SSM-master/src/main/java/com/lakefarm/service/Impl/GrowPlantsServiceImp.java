package com.lakefarm.service.Impl;

import com.lakefarm.mapper.GrowPlantsMapper;
import com.lakefarm.pojo.GrowPlants;
import com.lakefarm.service.GrowPlantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rxl on 17-2-23.
 */
@Service
public class GrowPlantsServiceImp implements GrowPlantsService{
    @Autowired
    private GrowPlantsMapper growPlantsMapper;
    @Override
    public int addGrow(GrowPlants t) {
        return growPlantsMapper.addGrow(t);
    }

    @Override
    public int deleteGrow(String zz_id) {
        return growPlantsMapper.deleteGrow(zz_id);
    }

    @Override
    public int updateGrow(GrowPlants t) {
        return growPlantsMapper.updateGrow(t);
    }

    @Override
    public GrowPlants getGrowById(String zz_id) {
        return growPlantsMapper.getGrowById(zz_id);
    }
}
