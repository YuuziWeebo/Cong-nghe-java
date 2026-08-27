package vn.edu.eaut.lab10.controller;

import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
import vn.edu.eaut.lab10.model.User;import vn.edu.eaut.lab10.repository.UserRepository;

@WebServlet("/user/profile")
public class ProfileController extends HttpServlet{
 private final UserRepository repo=new UserRepository();
 protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{r.getRequestDispatcher("/user/profile.jsp").forward(r,s);}
 protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{
  User sessionUser=(User)r.getSession().getAttribute("currentUser");
  User u=repo.find(sessionUser.getId());u.setFullName(r.getParameter("fullName"));u.setEmail(r.getParameter("email"));
  repo.update(u);r.getSession().setAttribute("currentUser",u);r.setAttribute("ok","Cập nhật hồ sơ thành công.");doGet(r,s);
 }
}
