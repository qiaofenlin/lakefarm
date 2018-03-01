package com.lakefarm.service;

import com.lakefarm.pojo.GrowPlants;

/**
 * Created by rxl on 17-2-23.
 */
public interface GrowPlantsService {
    /*
    *增加种植物
    */
    public int addGrow(GrowPlants t);
    /*
    *删除种植物
    */
    public int deleteGrow(String zz_id);

    /*
    *更新种植物
    */
    public int updateGrow(GrowPlants t);
    /*
    *通过id获取指定种植物
    */
    public GrowPlants getGrowById(String zz_id);
}
