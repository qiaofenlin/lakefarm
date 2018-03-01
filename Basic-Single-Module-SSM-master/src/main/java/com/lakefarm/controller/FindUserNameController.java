package com.lakefarm.controller;

import com.lakefarm.pojo.User;
import com.lakefarm.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by rxl on 17-2-23.
 */
@Controller
@RequestMapping("/lakeFarm")
public class FindUserNameController {
    private static Logger log = Logger.getLogger(FindUserNameController.class);
    @Autowired
    private UserService userService;
    @RequestMapping(value="/findUserName.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String findUserName(HttpServletRequest request, HttpServletResponse response) {
        log.info("获取用户信息-------");
        String username=request.getParameter("username").trim();
        List<User> userList=userService.getUserByUserName(username);
        try{
             int userListSize=userList.size();
            if (userListSize>1){
                return "2";
            }
            else {
                return "1";
            }
        }catch (Exception e){
            return "2";
        }

    }
}
