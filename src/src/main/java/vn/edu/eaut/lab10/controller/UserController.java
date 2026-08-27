package vn.edu.eaut.lab10.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import vn.edu.eaut.lab10.model.*;
import vn.edu.eaut.lab10.repository.UserRepository;

@WebServlet("/admin/users")
public class UserController extends HttpServlet {
    private final UserRepository repo=new UserRepository();

    protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        String edit=req.getParameter("edit");
        if(edit!=null){
            req.setAttribute("editUser",repo.find(Integer.parseInt(edit)));
        }
        req.setAttribute("users",repo.findAll());
        req.getRequestDispatcher("/admin/users.jsp").forward(req,resp);
    }
    protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        req.setCharacterEncoding("UTF-8");
        try{
            String action=req.getParameter("action");
            if("create".equals(action)){
                User u=new User(req.getParameter("email"),req.getParameter("password"),
                        req.getParameter("fullName"),Role.valueOf(req.getParameter("role")));
                u.setActive(true); repo.save(u);
            } else if("update".equals(action)){
                User u=repo.find(Integer.parseInt(req.getParameter("id")));
                u.setFullName(req.getParameter("fullName"));
                u.setEmail(req.getParameter("email"));
                u.setRole(Role.valueOf(req.getParameter("role")));
                u.setActive("on".equals(req.getParameter("active")));
                if(req.getParameter("password")!=null && !req.getParameter("password").isBlank())
                    u.setPassword(req.getParameter("password"));
                repo.update(u);
            }
            resp.sendRedirect(req.getContextPath()+"/admin/users?ok=1");
        }catch(Exception e){
            req.setAttribute("error","Không thể lưu tài khoản: "+e.getMessage());
            doGet(req,resp);
        }
    }
}
