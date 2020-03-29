package site.gladmin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

    @RequestMapping(path ="demo" )
    public String demo(){

        System.out.println("demo!!");
        return "success";
    }
}
