package io.ankara.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/11/16.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value="/", method= RequestMethod.GET)
    public String home() {
        return "home";
    }
}
