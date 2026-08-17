package vn.edu.eaut.lab6.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab6.model.Student;

import java.util.ArrayList;
import java.util.List;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        // Khởi tạo danh sách 5 sinh viên mẫu
        List<Student> initialStudents = new ArrayList<>();
        initialStudents.add(new Student("SV001", "Nguyen Van An", "DCCNTT12", "an@example.com"));
        initialStudents.add(new Student("SV002", "Tran Thi Binh", "DCCNTT12", "binh@example.com"));
        initialStudents.add(new Student("SV003", "Le Van Cuong", "DCCNTT13", "cuong@example.com"));
        initialStudents.add(new Student("SV004", "Pham Dung", "DCCNTT13", "dung@example.com"));
        initialStudents.add(new Student("SV005", "Hoang Anh", "DCCNTT14", "anh@example.com"));

        // Lưu danh sách sinh viên vào ServletContext với key "students"
        context.setAttribute("students", initialStudents);

        System.out.println("[AppContextListener] Ung dung Lab 6 da khoi dong. Da khoi tao 5 sinh vien mau vao ServletContext.");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        // Lấy danh sách từ ServletContext và ghi log số lượng khi dừng ứng dụng
        @SuppressWarnings("unchecked")
        List<Student> students = (List<Student>) context.getAttribute("students");

        int total = (students != null) ? students.size() : 0;

        System.out.println("[AppContextListener] Ung dung Lab 6 dang dung. Tong so sinh vien ghi nhan truoc khi dung: " + total);
    }
}