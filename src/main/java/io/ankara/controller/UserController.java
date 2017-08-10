package io.ankara.controller;

import io.ankara.domain.User;
import io.ankara.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/**
 * @author Boniface Chacha
 * @email boniface.chacha@niafikra.com
 * @email bonifacechacha@gmail.com
 * @date 9/12/16.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Inject
    private UserService userService;

    @RequestMapping(value="confirmEmail/{tokenID}", method= RequestMethod.GET)
    public String confirmEmail(@PathVariable("tokenID") String tokenID, Model model) {
        User user = userService.verify(tokenID);
        model.addAttribute("user",user);
        return "redirect:/app";
    }


}
