package com.lakefarm.controller;

import com.lakefarm.pojo.Question;
import com.lakefarm.service.QuestionService;
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
 * Created by rxl on 17-6-6.
 */
@Controller
@RequestMapping("/lakeFarm")
public class GetAllQuestionController {
    private static Logger log = Logger.getLogger(GetRandomQuestionController.class);
    @Autowired
    private QuestionService questionService;
    @RequestMapping(value="/GetAllQuestion.from", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public @ResponseBody
    String getRandomQuestion(HttpServletRequest request, HttpServletResponse response) {
        log.info("开始获取问题-------");
        try {
            List<Question> questionList=questionService.getAllQuestion();
            int size=questionList.size();
            String result="";
            for(int i=0;i<size;i++){
                Question q=questionList.get(i);
                result+=""+q.getQuestion_id()+"@%"+q.getQuestion_title()+"@%"+q.getQuestion_selection()+"@%"+q.getQuestion_answer()+"@$%";
            }
            return result;
        }catch (Exception e){
            log.error("异常信息位置",e);//TODO 网络异常
            return "100";
        }

    }
}
