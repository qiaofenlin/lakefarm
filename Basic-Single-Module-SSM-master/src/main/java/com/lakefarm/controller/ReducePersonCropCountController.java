package com.lakefarm.controller;

import com.lakefarm.pojo.PersonGrowPlants;
import com.lakefarm.pojo.User;
import com.lakefarm.service.PersonGrowPlantsService;
import com.lakefarm.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rxl on 17-2-23.
 */
@Controller
@RequestMapping("/lakeFarm")
public class ReducePersonCropCountController {
    private static Logger log = Logger.getLogger(ReducePersonCropCountController.class);
    @Autowired
    private PersonGrowPlantsService personGrowPlantsService;
    @RequestMapping(value="/ReducePersonCropCount.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getUser(HttpServletRequest request, HttpServletResponse response) {
        log.info("减少种植物数量-------");
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
                    String uzz_id=request.getParameter("uzz_id").trim();
                    log.info(user.getU_id()+"  "+uzz_id);
                    PersonGrowPlants p=personGrowPlantsService.getPersonGrowById(uzz_id);
                    p.setZz_count(String.valueOf(Integer.parseInt(p.getZz_count())-1));
                    personGrowPlantsService.updatePersonGrow(p);
                    PersonGrowPlants p1=personGrowPlantsService.getPersonGrowById(uzz_id);
                    int num=Integer.parseInt(p.getZz_count());
                    if (num==0){
                        personGrowPlantsService.deletePersonGrow(uzz_id);
                        log.info(uzz_id+"该种子已经使用完！");
                    }
//                    log.info(p.getZz_count());
                    return "1";//
                }
            }
            else {
                return "101";//TODO COOKIES失效
            }
        }
        catch (Exception e){
            log.error("异常错误位置----",e);
            return "100";//TODO 网络异常
        }
    }
}
