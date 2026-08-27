package vn.edu.eaut.lab10.controller;

import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
import vn.edu.eaut.lab10.model.Book;import vn.edu.eaut.lab10.repository.GenericRepository;

@WebServlet("/staff/books")
public class BookController extends HttpServlet{
 private final GenericRepository<Book> repo=new GenericRepository<>(Book.class);
 protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{r.setAttribute("items",repo.findAll());r.getRequestDispatcher("/staff/books.jsp").forward(r,s);}
 protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{
  r.setCharacterEncoding("UTF-8");try{String a=r.getParameter("action");
   if("delete".equals(a))repo.delete(Integer.parseInt(r.getParameter("id")));
   else {Book x="update".equals(a)?repo.find(Integer.parseInt(r.getParameter("id"))):new Book();x.setIsbn(r.getParameter("isbn"));x.setTitle(r.getParameter("title"));x.setAuthor(r.getParameter("author"));x.setQuantity(Integer.parseInt(r.getParameter("quantity")));if("update".equals(a))repo.update(x);else repo.save(x);}
   s.sendRedirect(r.getContextPath()+"/staff/books?ok=1");
  }catch(Exception e){r.setAttribute("error","Lỗi: "+e.getMessage());doGet(r,s);}
 }
}
