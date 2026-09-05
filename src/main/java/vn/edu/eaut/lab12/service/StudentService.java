package vn.edu.eaut.lab12.service;

import org.springframework.stereotype.Service;
import vn.edu.eaut.lab12.model.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class StudentService {
    private final List<Student> students = new ArrayList<>();
    private long nextId = 1;

    public StudentService() {
        save(new Student(null, "SV001", "Nguyễn Văn An", "an@example.com", "CNTT1"));
        save(new Student(null, "SV002", "Trần Thị Bình", "binh@example.com", "CNTT2"));
    }

    public List<Student> findAll() { return new ArrayList<>(students); }

    public Optional<Student> findById(Long id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) return findAll();
        String q = keyword.trim().toLowerCase(Locale.ROOT);
        return students.stream()
                .filter(s -> s.getFullName() != null && s.getFullName().toLowerCase(Locale.ROOT).contains(q))
                .toList();
    }

    public boolean existsByStudentCode(String code, Long excludeId) {
        if (code == null) return false;
        return students.stream().anyMatch(s ->
            code.trim().equalsIgnoreCase(s.getStudentCode()) &&
            (excludeId == null || !s.getId().equals(excludeId)));
    }

    public void save(Student student) {
        if (student.getId() == null) {
            student.setId(nextId++);
            students.add(student);
        } else {
            findById(student.getId()).ifPresent(existing -> {
                existing.setStudentCode(student.getStudentCode());
                existing.setFullName(student.getFullName());
                existing.setEmail(student.getEmail());
                existing.setClassName(student.getClassName());
            });
        }
    }

    public boolean deleteById(Long id) {
        return students.removeIf(s -> s.getId().equals(id));
    }
}