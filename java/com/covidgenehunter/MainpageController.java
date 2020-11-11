package com.covidgenehunter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("")
@Controller
public class MainpageController {

    @RequestMapping(method = RequestMethod.GET)
    public String renderMainPage(ModelMap model) {
        model.addAttribute("message", "Future Main Page of Gene App");
        return "mainpage";
    }

}
