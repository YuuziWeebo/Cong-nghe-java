package vn.edu.eaut.lab12.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.edu.eaut.lab12.model.Student;
import vn.edu.eaut.lab12.service.StudentService;

@Controller
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String list(@RequestParam(required=false) String keyword, Model model) {
        model.addAttribute("students", studentService.searchByName(keyword));
        model.addAttribute("keyword", keyword == null ? "" : keyword);
        return "students/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model model) {
        return studentService.findById(id)
                .map(s -> { model.addAttribute("student", s); return "students/detail"; })
                .orElse("redirect:/students");
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("formTitle", "Thêm sinh viên");
        return "students/form";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        return studentService.findById(id)
                .map(s -> {
                    model.addAttribute("student", s);
                    model.addAttribute("formTitle", "Sửa sinh viên");
                    return "students/form";
                }).orElse("redirect:/students");
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("student") Student student,
                       BindingResult result, Model model) {
        if (studentService.existsByStudentCode(student.getStudentCode(), student.getId())) {
            result.rejectValue("studentCode", "duplicate", "Mã sinh viên đã tồn tại");
        }
        if (result.hasErrors()) {
            model.addAttribute("formTitle", student.getId() == null ? "Thêm sinh viên" : "Sửa sinh viên");
            return "students/form";
        }
        studentService.save(student);
        return "redirect:/students";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        studentService.deleteById(id);
        return "redirect:/students";
    }
}