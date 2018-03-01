package com.lakefarm.controller;

import com.lakefarm.pojo.Record;
import com.lakefarm.pojo.User;
import com.lakefarm.service.RecordService;
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
import java.util.List;

/**
 * Created by rxl on 17-6-6.
 */
@Controller
@RequestMapping("/lakeFarm")
public class GetUserRecordController {
    private static Logger log = Logger.getLogger(GetUserRecordController.class);
    @Autowired
    private RecordService recordService;
    @Autowired
    private UserService userService;
    @RequestMapping(value="/GetUserRecord.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getUser(HttpServletRequest request, HttpServletResponse response) {
        log.info("获取record-------");
        String u_id="";
        try {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().trim().equals("u_id")) {
                        u_id=c.getValue().trim();//对u_id进行赋值
                    }
                }
                if ("".equals(u_id.trim())) {
                    return new String("102");//TODO COOKIES失效
                }
                else {
                    //建立映射
                    User u=userService.getUserById(u_id);
                    List<Record> recordList=recordService.getRecordById(u_id);
                    int size=recordList.size();
                    String res="";
                    for (int i=0;i<size;i++){
                        res+=recordList.get(i).getRecord_id()+"#$"+recordList.get(i).getRecord_time()
                                +"#$"+recordList.get(i).getRecord_score()+"#$"+
                                recordList.get(i).getQuestion_type()+"#$"+u.getU_name()+"#&";
                    }
                    return res;
                }
            }
        }catch (Exception e){
            log.error("异常错误位置----",e);
            return new String("100"); //TODO 获取record fail
        }
    return null;

    }
}
