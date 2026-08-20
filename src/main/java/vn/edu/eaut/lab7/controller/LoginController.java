package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab7.model.User;
import vn.edu.eaut.lab7.repository.UserRepository;

import java.io.IOException;

@WebServlet("/lab7-login")
public class LoginController extends HttpServlet {

    private UserRepository userRepo = new UserRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User user = userRepo.checkLogin(username, password);

        if (user != null) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // Lưu nguyên Object User
            response.sendRedirect(request.getContextPath() + "/dashboard.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=invalid");
        }
    }
}