package com.lakefarm.service;

import com.lakefarm.pojo.GrowPlantsCustom;

/**
 * Created by docker on 2017/2/23.
 */
public interface GrowplantsShopService {
    public GrowPlantsCustom selectGrowplantInfo(Integer zzId) throws Exception;
    public Integer selectGrowplantCount(Integer zzId) throws Exception;
    public Integer selectGrowplantsByZzId(Integer zzId) throws Exception;
}
