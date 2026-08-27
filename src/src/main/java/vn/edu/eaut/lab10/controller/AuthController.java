package vn.edu.eaut.lab10.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import vn.edu.eaut.lab10.model.User;
import vn.edu.eaut.lab10.service.AuthService;

@WebServlet("/auth")
public class AuthController extends HttpServlet {
    private final AuthService authService=new AuthService();
    protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        request.setCharacterEncoding("UTF-8");
        String email=request.getParameter("email");
        String password=request.getParameter("password");
        User user=authService.login(email,password);
        if(user==null){
            request.setAttribute("error","Email hoặc mật khẩu không đúng / tài khoản bị khóa");
            request.getRequestDispatcher("/login.jsp").forward(request,response); return;
        }
        request.getSession(true).setAttribute("currentUser",user);
        response.sendRedirect(request.getContextPath()+"/dashboard.jsp");
    }
    protected void doGet(HttpServletRequest request,HttpServletResponse response)throws IOException{
        if("logout".equals(request.getParameter("action"))){
            HttpSession s=request.getSession(false); if(s!=null)s.invalidate();
        }
        response.sendRedirect(request.getContextPath()+"/login.jsp");
    }
}
