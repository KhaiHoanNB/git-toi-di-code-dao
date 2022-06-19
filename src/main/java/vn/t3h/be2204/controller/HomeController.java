package vn.t3h.be2204.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller// đánh dấu nó là 1 controller
public class HomeController {

    @RequestMapping({"home", "","/", "home/abc"})
//    @ResponseBody
    public String home(){
        return "home";
    }

    @RequestMapping("home/{path}")
//    @ResponseBody
    public String homeModel(Model model, @PathVariable String path,
                            @RequestParam(required = false) String key){
        model.addAttribute("p", path);
        model.addAttribute("k", key);
        List<String> listStr = Arrays.asList(new String[] {"Nguyễn Văn A", "Trần Thị X", "Lên Văn D"});
        model.addAttribute("list", listStr);
        return "/WEB-INF/jsp/home.jsp";
    }

    @RequestMapping("login")
    public String login(){
        return "login";
    }

    @RequestMapping("signin")
    public String signin(){
        return "signin";
    }
}
