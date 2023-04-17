package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RolesService;
import ru.kata.spring.boot_security.demo.services.UsersService;

import java.security.Principal;

@Controller
public class MainController {
    private UsersService usersService;

    private RolesService rolesService;

    @Autowired
    public MainController(UsersService usersService, RolesService rolesService) {
        this.usersService = usersService;
        this.rolesService = rolesService;
    }

    @GetMapping("/")
    public String indexPage() {
        System.out.println(rolesService.show(1l).getRole_id());
        return "index";
    }

    @GetMapping("/user")
    public String pageForAuthenticatedUsers(Principal principal, Model model) {
        User user = usersService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }
}
