package vn.edu.eaut.lab10.controller;

import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
import vn.edu.eaut.lab10.model.User;import vn.edu.eaut.lab10.repository.UserRepository;

@WebServlet("/user/password")
public class PasswordController extends HttpServlet{
 private final UserRepository repo=new UserRepository();
 protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{r.getRequestDispatcher("/user/password.jsp").forward(r,s);}
 protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{
  User sessionUser=(User)r.getSession().getAttribute("currentUser");User u=repo.find(sessionUser.getId());
  String old=r.getParameter("oldPassword"), n=r.getParameter("newPassword"), c=r.getParameter("confirmPassword");
  if(!u.getPassword().equals(old)){r.setAttribute("error","Mật khẩu cũ không đúng.");doGet(r,s);return;}
  if(n==null||n.length()<6||!n.equals(c)){r.setAttribute("error","Mật khẩu mới phải từ 6 ký tự và xác nhận phải trùng.");doGet(r,s);return;}
  u.setPassword(n);repo.update(u);r.getSession().setAttribute("currentUser",u);r.setAttribute("ok","Đổi mật khẩu thành công.");doGet(r,s);
 }
}
