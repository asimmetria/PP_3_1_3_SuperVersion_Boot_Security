package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.models.UserAndRoles;
import ru.kata.spring.boot_security.demo.services.ItemsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/admin")
public class AdminController {
    private ItemsService<User> usersService;
    private PasswordEncoder passwordEncoder;
    private ItemsService<Role> rolesService;

    @Autowired
    public AdminController(ItemsService<User> usersService, PasswordEncoder passwordEncoder, ItemsService<Role> rolesService) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.rolesService = rolesService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", usersService.index());
        return "admin/index";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("userAndRoles") UserAndRoles userAndRoles, Model model) {
        model.addAttribute("userAndRoles", userAndRoles);
        model.addAttribute("allRoles", rolesService.index());
        return "admin/new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("userAndRoles") UserAndRoles userAndRoles) {
        User user = userAndRoles.getUser();
        List<Long> selectedRoles = userAndRoles.getSelectedRoles();
        List<Role> userRoles = rolesService.index().stream()
                .filter(role -> selectedRoles.contains(role.getRole_id()))
                .collect(Collectors.toList());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        usersService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") Long user_id) {
        UserAndRoles userAndRoles = new UserAndRoles();
        User user = usersService.show(user_id);
        userAndRoles.setUser(user);
        userAndRoles.setSelectedRoles(user.getRoles().stream().map(Role::getRole_id).collect(Collectors.toList()));

        model.addAttribute("userAndRoles", userAndRoles);
        model.addAttribute("allRoles", rolesService.index());

        return "admin/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("userAndRoles") UserAndRoles userAndRoles,
                         @PathVariable("id") Long id) {
        User user = userAndRoles.getUser();
        List<Long> selectedRoles = userAndRoles.getSelectedRoles();
        List<Role> userRoles = rolesService.index().stream()
                .filter(role -> selectedRoles.contains(role.getRole_id()))
                .collect(Collectors.toList());
        user.setUser_id(id);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        usersService.update(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long user_id) {
        usersService.delete(user_id);
        return "redirect:/admin";

    }
}
