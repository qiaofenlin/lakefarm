package com.lakefarm.mapper;

import com.lakefarm.pojo.GrowPlants;

import java.util.List;

/**
 * Created by rxl on 17-2-23.
 */
public interface GrowPlantsMapper {
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
