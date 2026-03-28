package ru.mtuci.coursemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtuci.coursemanagement.model.Student;
import ru.mtuci.coursemanagement.repository.StudentRepository;

@Controller
@RequiredArgsConstructor
public class StudentsController {
    private final StudentRepository studentRepository;

    @GetMapping("/students")
    public String list(Model model) {
        model.addAttribute("students", studentRepository.findAll());
        return "students";
    }

    @PostMapping("/students")
    public String add(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam(required = false) Long userId) {
        Student s = new Student();
        s.setName(name);
        s.setEmail(email);
        s.setUserId(userId);
        studentRepository.save(s);
        return "redirect:/students";
    }
}
