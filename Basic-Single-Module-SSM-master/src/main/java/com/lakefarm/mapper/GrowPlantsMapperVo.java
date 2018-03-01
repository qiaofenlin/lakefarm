package com.lakefarm.mapper;

import com.lakefarm.pojo.GrowPlantsCustom;
import com.lakefarm.pojo.GrowPlantsExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GrowPlantsMapperVo {
    int countByExample(GrowPlantsExample example);

    int deleteByExample(GrowPlantsExample example);

    int deleteByPrimaryKey(Integer zzId);

    int insert(GrowPlantsCustom record);

    int insertSelective(GrowPlantsCustom record);

    List<GrowPlantsCustom> selectByExample(GrowPlantsExample example);

    GrowPlantsCustom selectByPrimaryKey(Integer zzId);

    int updateByExampleSelective(@Param("record") GrowPlantsCustom record, @Param("example") GrowPlantsExample example);

    int updateByExample(@Param("record") GrowPlantsCustom record, @Param("example") GrowPlantsExample example);

    int updateByPrimaryKeySelective(GrowPlantsCustom record);

    int updateByPrimaryKey(GrowPlantsCustom record);
}