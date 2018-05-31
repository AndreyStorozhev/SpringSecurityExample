package my.example.controller;

import my.example.entity.User;
import my.example.service.SecurityService;
import my.example.service.UserService;
import my.example.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new User());
        return "registration";
    }
    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User user, BindingResult result){
        userValidator.validate(user, result);
        if (result.hasErrors())
            return "registration";
        userService.save(user);
        securityService.autoLogin(user.getUsername(), user.getConfirmPassword());
        return "redirect:/welcome";
    }
    @GetMapping("/login")
    public String login(Model model, String error, String logout){
        if (error != null)
            model.addAttribute("error", "Username or password is incorrect");
        if (logout != null)
            model.addAttribute("message", "Logged out successfully");
        return "login";
    }
    @RequestMapping({"/", "/welcome"})
    public String welcome(){
        return "welcome";
    }
    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
}
