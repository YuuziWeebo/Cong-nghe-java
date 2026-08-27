package vn.edu.eaut.lab10.controller;

import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
import vn.edu.eaut.lab10.model.Student;import vn.edu.eaut.lab10.repository.GenericRepository;

@WebServlet("/staff/students")
public class StudentController extends HttpServlet{
 private final GenericRepository<Student> repo=new GenericRepository<>(Student.class);
 protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{
  r.setAttribute("items",repo.findAll());r.getRequestDispatcher("/staff/students.jsp").forward(r,s);
 }
 protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{
  r.setCharacterEncoding("UTF-8");String action=r.getParameter("action");
  try{
   if("delete".equals(action)) repo.delete(Integer.parseInt(r.getParameter("id")));
   else if("update".equals(action)){Student x=repo.find(Integer.parseInt(r.getParameter("id")));fill(x,r);repo.update(x);}
   else {Student x=new Student();fill(x,r);repo.save(x);}
   s.sendRedirect(r.getContextPath()+"/staff/students?ok=1");
  }catch(Exception e){r.setAttribute("error","Lỗi: "+e.getMessage());doGet(r,s);}
 }
 private void fill(Student x,HttpServletRequest r){x.setCode(r.getParameter("code"));x.setFullName(r.getParameter("fullName"));x.setEmail(r.getParameter("email"));x.setMajor(r.getParameter("major"));}
}
