package com.lakefarm.controller;

import com.lakefarm.pojo.GrowPlants;
import com.lakefarm.pojo.PersonGrowPlants;
import com.lakefarm.pojo.User;
import com.lakefarm.service.GrowPlantsService;
import com.lakefarm.service.PersonGrowPlantsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by rxl on 17-2-23.
 */
@Controller
@RequestMapping("/lakeFarm")
public class GetPersonGrowPlantsController {
    private static Logger log = Logger.getLogger(GetPersonGrowPlantsController.class);
    @Autowired
    private PersonGrowPlantsService personGrowPlantsService;
    @Autowired
    private GrowPlantsService growPlantsService;

    @RequestMapping(value = "/GetPersonGrowPlants.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public
    @ResponseBody
    String GetPersonGrowPlants(HttpServletRequest request, HttpServletResponse response) {
        log.info("获取种子-------");
        try {
            Cookie[] cookies = request.getCookies();
            User user = new User();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().trim().equals("u_id")) {
                        user.setU_id(c.getValue().trim());//对u_id进行赋值
                    }
                }
                if ("".equals(user.getU_id())) {
                    return new String("102");//TODO COOKIES失效
                }
                else {
                    List<PersonGrowPlants> seedList=personGrowPlantsService.getPersonGrowByUid(user.getU_id());
                    int size=seedList.size();
                    String result="";
                    for(int i=0;i<size;i++) {
                        PersonGrowPlants p = seedList.get(i);
                        GrowPlants g = growPlantsService.getGrowById(p.getZz_id());
                        result+="uzz_id$*"+p.getUzz_id()+"$*zz_icon$*"+g.getZz_icon()+"$*zz_count$*"+p.getZz_count()+"#$%";
                    }
                    log.info(result);
                    return result;

                }
            }
            else {
                return "101";//TODO COOKIES失效
            }
        } catch (Exception e) {
            log.error("异常信息－－－",e);
            return "100";//TODO 网络异常
        }
    }
}
