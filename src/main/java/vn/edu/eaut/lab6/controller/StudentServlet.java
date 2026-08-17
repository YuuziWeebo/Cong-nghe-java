package vn.edu.eaut.lab6.controller;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab6.model.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    private List<Student> getStudentsFromContext() {
        ServletContext context = getServletContext();
        List<Student> list = (List<Student>) context.getAttribute("students");
        if (list == null) {
            list = new ArrayList<>();
            context.setAttribute("students", list);
        }
        return list;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        List<Student> allStudents = getStudentsFromContext();

        String keyword = request.getParameter("keyword");
        List<Student> filteredList = new ArrayList<>();

        // Lọc tìm kiếm
        if (keyword != null && !keyword.trim().isEmpty()) {
            String lowerKeyword = keyword.trim().toLowerCase();
            for (Student s : allStudents) {
                if (s.getName() != null && s.getName().toLowerCase().contains(lowerKeyword)) {
                    filteredList.add(s);
                }
            }
        } else {
            filteredList = allStudents;
        }

        request.setAttribute("keyword", keyword);
        request.setAttribute("students", filteredList);

        request.getRequestDispatcher("/student-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        List<Student> students = getStudentsFromContext();

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String className = request.getParameter("className");
        String email = request.getParameter("email");

        // Thêm sinh viên mới vào ServletContext
        students.add(new Student(id, name, className, email));

        response.sendRedirect(request.getContextPath() + "/students");
    }
}