package ru.mtuci.coursemanagement.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.mtuci.coursemanagement.model.Course;
import ru.mtuci.coursemanagement.service.CourseService;

@Controller
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/courses")
    public String list(Model model) {
        model.addAttribute("courses", courseService.findAll());
        return "courses";
    }

    @PostMapping("/courses")
    public String add(
            @RequestParam String title,
            @RequestParam String description,
            @RequestParam(required = false) Long teacherId) {
        Course c = new Course();
        c.setTitle(title);
        c.setDescription(description);
        c.setTeacherId(teacherId != null ? teacherId : 1L);
        courseService.save(c);
        return "redirect:/courses";
    }
}
