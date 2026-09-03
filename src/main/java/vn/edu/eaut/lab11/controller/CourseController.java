package vn.edu.eaut.lab11.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vn.edu.eaut.lab11.model.Course;

import java.util.List;

@Controller
public class CourseController {
    @GetMapping("/courses")
    public String listCourses(Model model) {
        List<Course> courses = List.of(
            new Course("IT3242", "Công nghệ Java", 3),
            new Course("IT3030", "Cơ sở dữ liệu", 3),
            new Course("IT3090", "Lập trình hướng đối tượng", 3),
            new Course("IT3050", "Phát triển ứng dụng web", 3),
            new Course("IT3170", "Công nghệ phần mềm", 3)
        );
        model.addAttribute("courses", courses);
        return "courses";
    }
}
