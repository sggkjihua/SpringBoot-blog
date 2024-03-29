package com.xiaolin.blog.controller.admin;

import com.xiaolin.blog.model.User;
import com.xiaolin.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;
    @GetMapping()
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
                        HttpSession httpSession, RedirectAttributes redirectAttributes) {
        User user = this.userService.checkUser(email, password);
        if(user !=null){
            httpSession.setAttribute("user", user);
            return "admin/index";
        }
        redirectAttributes.addFlashAttribute("message", "Authentication Failed");
        return "redirect:/admin";
    }
    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("user");
        return "redirect:/admin";
    }

    @GetMapping("/*")
    public String defaultPage(){
        return "admin/index";
    }
}
