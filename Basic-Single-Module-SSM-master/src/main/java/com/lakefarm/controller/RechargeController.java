package com.lakefarm.controller;

import com.lakefarm.pojo.User;
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
public class RechargeController {
    private static Logger log = Logger.getLogger(RechargeController.class);
    @Autowired
    private UserService userService;
    @RequestMapping(value="/Recharge.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String recharge(HttpServletRequest request, HttpServletResponse response) {
        log.info("充值信息-------");
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
                }else {
                    int diamond=Integer.parseInt(request.getParameter("diamond").trim());
                    log.info(diamond);
                    User u1=userService.getUserById(user.getU_id());
                    if (u1.getU_money()>=diamond){
                        //钻石增加
                        u1.setU_diamond(diamond+u1.getU_diamond());
                        //钱减少
                        u1.setU_money(u1.getU_money()-diamond);
                        userService.update(u1);
                        log.info("充值成功"+diamond);
                        return "1";
                    }else {
                        return "104";//TODO 钱不够　去充钱吧
                    }

                }
            }else {
                return "101";//TODO COOKIES失效
            }
        }catch (Exception e){
            log.error("异常错误位置----",e);
            return new String("100"); //TODO 网络异常
        }

    }

}
