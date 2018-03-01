package com.lakefarm.service;

import com.lakefarm.mapper.VegetableBlockMapper;

/**
 * Created by rxl on 17-2-22.
 */
public interface VegetableBlockService {
    /*
    *增加菜地块
    */
    public int addBlock(VegetableBlockMapper t);


    /*
    *删除菜地块
    */
    public int deleteBlock(String block_id);

    /*
    *更新菜地块
    */
    public int updateBlock(VegetableBlockMapper t);


    /*
    *通过id获取指定菜地块
    */
    public VegetableBlockMapper getBlockById(String block_id);
}
