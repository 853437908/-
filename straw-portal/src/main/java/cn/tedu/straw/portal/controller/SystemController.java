package cn.tedu.straw.portal.controller;

import cn.tedu.straw.portal.security.UserInfo;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {

    @GetMapping("/question/detail.html")
    public String questionDetail(){
        return "question/detail";
    }


    @GetMapping("/login.html")
    public String login(){
        return "login";
    }

    @GetMapping("/register.html")
    public String register(){
        return "register";
    }

    @GetMapping("/question/create.html")
    public String createQuestion(){
        return "question/create";
    }

    @GetMapping("/index.html")
    public  String indext(@AuthenticationPrincipal UserInfo userInfo){
        if(userInfo.getType()==0){
            return "index";
        }else{
            return "index_teacher";
        }


    }

}
