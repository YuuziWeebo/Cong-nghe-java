package vn.edu.eaut.lab6.store;

import vn.edu.eaut.lab6.model.Student;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentStore {
    private static final List<Student> students = new ArrayList<>();

    static {
        students.add(new Student("SV001", "Nguyen Van An", "DCCNTT12", "an@example.com"));
        students.add(new Student("SV002", "Tran Thi Binh", "DCCNTT12", "binh@example.com"));
        students.add(new Student("SV003", "Le Van Cuong", "DCCNTT13", "cuong@example.com"));
    }

    public static List<Student> findAll() {
        return students;
    }

    public static void add(Student student) {
        students.add(student);
    }

    public static List<Student> searchByName(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return students;
        }
        List<Student> result = new ArrayList<>();
        String lowerKeyword = keyword.trim().toLowerCase();

        for (Student s : students) {
            if (s.getName() != null && s.getName().toLowerCase().contains(lowerKeyword)) {
                result.add(s);
            }
        }
        return result;
    }

    public static void deleteById(String id) {
        if (id != null) {
            students.removeIf(student -> id.equals(student.getId()));
        }
    }

    public static Student findById(String id) {
        if (id == null) return null;
        for (Student s : students) {
            if (id.equals(s.getId())) {
                return s;
            }
        }
        return null;
    }

    public static void update(Student updatedStudent) {
        if (updatedStudent == null || updatedStudent.getId() == null) return;
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(updatedStudent.getId())) {
                students.set(i, updatedStudent);
                break;
            }
        }
    }

    // --- Bổ sung cho Bài 10: Thống kê số lượng sinh viên theo từng lớp ---
    public static Map<String, Integer> getStudentCountByClass() {
        Map<String, Integer> countByClass = new HashMap<>();
        for (Student s : students) {
            String className = (s.getClassName() != null && !s.getClassName().trim().isEmpty())
                    ? s.getClassName().trim() : "Chưa xếp lớp";
            countByClass.put(className, countByClass.getOrDefault(className, 0) + 1);
        }
        return countByClass;
    }
}