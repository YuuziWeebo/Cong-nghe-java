package vn.edu.eaut.lab10.controller;

import jakarta.servlet.*;import jakarta.servlet.annotation.WebServlet;import jakarta.servlet.http.*;import java.io.IOException;
import vn.edu.eaut.lab10.model.Product;import vn.edu.eaut.lab10.repository.GenericRepository;

@WebServlet("/staff/products")
public class ProductController extends HttpServlet{
 private final GenericRepository<Product> repo=new GenericRepository<>(Product.class);
 protected void doGet(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{r.setAttribute("items",repo.findAll());r.getRequestDispatcher("/staff/products.jsp").forward(r,s);}
 protected void doPost(HttpServletRequest r,HttpServletResponse s)throws ServletException,IOException{
  r.setCharacterEncoding("UTF-8");try{String a=r.getParameter("action");
   if("delete".equals(a))repo.delete(Integer.parseInt(r.getParameter("id")));
   else {Product x="update".equals(a)?repo.find(Integer.parseInt(r.getParameter("id"))):new Product();x.setCode(r.getParameter("code"));x.setName(r.getParameter("name"));x.setPrice(Double.parseDouble(r.getParameter("price")));x.setQuantity(Integer.parseInt(r.getParameter("quantity")));if("update".equals(a))repo.update(x);else repo.save(x);}
   s.sendRedirect(r.getContextPath()+"/staff/products?ok=1");
  }catch(Exception e){r.setAttribute("error","Lỗi: "+e.getMessage());doGet(r,s);}
 }
}
