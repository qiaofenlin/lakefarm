package com.lakefarm.service;

import com.lakefarm.pojo.PersonGrowPlants;

import java.util.List;

/**
 * Created by rxl on 17-2-23.
 */
public interface PersonGrowPlantsService {
    /*
     *增加个人种植物
     */
    public int addPersonGrow(PersonGrowPlants t);
    /*
    *删除个人种植物
    */
    public int deletePersonGrow(String uzz_id);
    /*
    *更新个人种植物
    */
    public int updatePersonGrow(PersonGrowPlants t);
    /*
    *通过uzz_id获取指定个人种植物
    */
    public PersonGrowPlants getPersonGrowById(String uzz_id);
    /*
   *通过ｉｄ获取用户所有种子
    */
    public List<PersonGrowPlants> getPersonGrowByUid(String u_id);

    /*
   *通过uzz_id u_id获取用户所有种子
    */
    public PersonGrowPlants getPersonGrowByUzzid(String u_id,String uzz_id);
}
