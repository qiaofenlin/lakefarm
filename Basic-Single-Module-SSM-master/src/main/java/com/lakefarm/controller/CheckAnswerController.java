package com.lakefarm.controller;

import com.lakefarm.pojo.Question;
import com.lakefarm.pojo.Record;
import com.lakefarm.pojo.User;
import com.lakefarm.service.QuestionService;
import com.lakefarm.service.RecordService;
import com.lakefarm.service.UserService;
import com.lakefarm.utils.GetRandom;
import com.lakefarm.utils.TimeUtils;
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
 * Created by rxl on 17-2-25.
 */
@Controller
@RequestMapping("/lakeFarm")
public class CheckAnswerController {
    private static Logger log = Logger.getLogger(CheckAnswerController.class);
    @Autowired
    private QuestionService questionService;
    @Autowired
    private RecordService recordService;
    @RequestMapping(value="/CheckAnswer.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String checkAnswer(HttpServletRequest request, HttpServletResponse response) {
        log.info("检查答案-------");
        int correctnum=0;
        String checkstr=request.getParameter("check").trim();
        try {
            Cookie[] cookies = request.getCookies();
            String u_id="";
            if (cookies != null) {
                for (Cookie c : cookies) {
                    if (c.getName().trim().equals("u_id")) {
                        u_id=c.getValue().trim();//对u_id进行赋值
                    }
                }
                if ("".equals(u_id.trim())) {
                    return new String("102");//TODO COOKIES失效
                }
            }
            String check[]=checkstr.split("\\|");
            int size=check.length;
            for (int i=0;i<size;i++){
                String single[]=check[i].split("\\:");
//            log.info(single[0]);
//            log.info(single[1]);
                Question q=questionService.getQuestionById(single[0]);
                if (q.getQuestion_answer().trim().equals(single[1].trim())){
                    correctnum++;
                }

            }
            Record record=new Record();
            record.setQuestion_type("math");
            record.setU_id(u_id);
            record.setRecord_id(GetRandom.getUserId());
            record.setRecord_score(String.valueOf(correctnum).trim());
            record.setRecord_time(TimeUtils.GetCurrentTime());
            log.info("====="+correctnum);
            recordService.addRecord(record);
            log.info(checkstr);
            return ""+correctnum+"#$%"+(size-correctnum)+"";
        }catch (Exception e){
            log.error("异常信息位置------",e);//TODO 网络异常
            return "100";
        }

    }
}
