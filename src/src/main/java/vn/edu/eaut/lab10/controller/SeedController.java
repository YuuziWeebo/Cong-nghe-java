package vn.edu.eaut.lab10.controller;

import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
import vn.edu.eaut.lab10.model.*;import vn.edu.eaut.lab10.repository.*;

@WebServlet("/seed")
public class SeedController extends HttpServlet{
 protected void doGet(HttpServletRequest r,HttpServletResponse s)throws IOException{
  UserRepository ur=new UserRepository();
  if(ur.findByEmail("admin@eaut.edu.vn")==null)ur.save(new User("admin@eaut.edu.vn","123456","Quản trị viên",Role.ADMIN));
  if(ur.findByEmail("staff@eaut.edu.vn")==null)ur.save(new User("staff@eaut.edu.vn","123456","Nhân viên",Role.STAFF));
  if(ur.findByEmail("user@eaut.edu.vn")==null)ur.save(new User("user@eaut.edu.vn","123456","Người dùng",Role.USER));
  GenericRepository<Student> sr=new GenericRepository<>(Student.class);if(sr.findAll().isEmpty()){sr.save(new Student("SV001","Nguyễn Văn An","an@example.com","Công nghệ phần mềm"));sr.save(new Student("SV002","Trần Thị Bình","binh@example.com","Khoa học máy tính"));}
  GenericRepository<Book> br=new GenericRepository<>(Book.class);if(br.findAll().isEmpty()){br.save(new Book("978001","Lập trình Java","Nguyễn A",10));br.save(new Book("978002","Jakarta EE","Trần B",7));}
  GenericRepository<Product> pr=new GenericRepository<>(Product.class);if(pr.findAll().isEmpty()){pr.save(new Product("SP001","Laptop",15000000,5));pr.save(new Product("SP002","Chuột",350000,20));}
  s.sendRedirect(r.getContextPath()+"/login.jsp?seed=1");
 }
}
