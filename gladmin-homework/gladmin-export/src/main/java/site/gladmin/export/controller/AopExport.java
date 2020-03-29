package site.gladmin.export.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: gladmin
 * @Date: 2020/3/29 4:25 下午
 */
@Controller
@RequestMapping("demo")
public class AopExport {




    @ResponseBody
    @RequestMapping("/export")
//    @ExportExl
    public int getExportByList(String param){

        return 1;
    }
}
