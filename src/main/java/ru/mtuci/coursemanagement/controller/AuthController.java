package ru.mtuci.coursemanagement.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtuci.coursemanagement.model.User;
import ru.mtuci.coursemanagement.service.UserService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {
        return userService
                .findByUsername(username)
                .filter(u -> password != null && password.equals(u.getPassword()))
                .map(u -> {
                    session.setAttribute("user", u);
                    return "redirect:/";
                })
                .orElseGet(() -> {
                    model.addAttribute("error", "Неверные учётные данные");
                    return "login";
                });
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam(required = false) String role) {
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        u.setRole(role != null && !role.isBlank() ? role : "STUDENT");
        userService.save(u);
        return "redirect:/login";
    }
}
