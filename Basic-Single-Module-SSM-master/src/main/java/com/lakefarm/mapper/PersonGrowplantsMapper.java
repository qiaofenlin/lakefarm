package com.lakefarm.mapper;

import com.lakefarm.pojo.PersonGrowplantsCustom;
import com.lakefarm.pojo.PersonGrowplantsExample;
import com.lakefarm.pojo.PersonGrowplantsKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonGrowplantsMapper {
    int countByExample(PersonGrowplantsExample example);

    int deleteByExample(PersonGrowplantsExample example);

    int deleteByPrimaryKey(PersonGrowplantsKey key);

    int insert(PersonGrowplantsCustom record);

    int insertSelective(PersonGrowplantsCustom record);

    List<PersonGrowplantsCustom> selectByExample(PersonGrowplantsExample example);

    PersonGrowplantsCustom selectByPrimaryKey(PersonGrowplantsKey key);

    int updateByExampleSelective(@Param("record") PersonGrowplantsCustom record, @Param("example") PersonGrowplantsExample example);

    int updateByExample(@Param("record") PersonGrowplantsCustom record, @Param("example") PersonGrowplantsExample example);

    int updateByPrimaryKeySelective(PersonGrowplantsCustom record);

    int updateByPrimaryKey(PersonGrowplantsCustom record);
}