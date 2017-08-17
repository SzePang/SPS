package com.szepang.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Sze on 07/08/2017.
 */

@Controller
public class HelloController {

    @RequestMapping("/welcome/{countryname}/{username}")
    public ModelAndView helloWorld(@PathVariable("username") String name, @PathVariable("countryname") String country) {

        ModelAndView model = new ModelAndView("HelloPage");
        model.addObject("msg1", "Hi " +name+ " , welcome to my " +country);
        return model;
    }





}
