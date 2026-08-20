package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab7.model.SanPham;
import vn.edu.eaut.lab7.repository.SanPhamRepository;

import java.io.IOException;

@WebServlet("/san-pham")
public class SanPhamController extends HttpServlet {
    private final SanPhamRepository repo = new SanPhamRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("new".equals(action)) {
            req.getRequestDispatcher("/views/sanpham/form.jsp").forward(req, resp);
            return;
        }

        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("sp", repo.findById(id));
            req.getRequestDispatcher("/views/sanpham/form.jsp").forward(req, resp);
            return;
        }

        if ("detail".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("sp", repo.findById(id));
            req.getRequestDispatcher("/views/sanpham/detail.jsp").forward(req, resp);
            return;
        }

        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            repo.delete(id);
            resp.sendRedirect(req.getContextPath() + "/san-pham");
            return;
        }

        String keyword = req.getParameter("keyword");
        req.setAttribute("dsSanPham", repo.search(keyword));
        req.getRequestDispatcher("/views/sanpham/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String idStr = req.getParameter("id");
        int id = (idStr != null && !idStr.isBlank()) ? Integer.parseInt(idStr) : 0;

        String maSanPham = req.getParameter("maSanPham");
        String tenSanPham = req.getParameter("tenSanPham");
        String moTa = req.getParameter("moTa");
        
        double gia = 0;
        int soLuong = 0;
        String errorMsg = null;

        // Ép kiểu & Validate dữ liệu
        try {
            gia = Double.parseDouble(req.getParameter("gia"));
            soLuong = Integer.parseInt(req.getParameter("soLuong"));

            if (gia <= 0) {
                errorMsg = "Giá sản phẩm phải lớn hơn 0!";
            } else if (soLuong < 0) {
                errorMsg = "Số lượng sản phẩm không được nhỏ hơn 0!";
            }
        } catch (NumberFormatException e) {
            errorMsg = "Giá và số lượng phải là định dạng số!";
        }

        SanPham sp = new SanPham(id, maSanPham, tenSanPham, moTa, gia, soLuong);

        // Nếu có lỗi validate -> Quay lại form hiển thị thông báo lỗi
        if (errorMsg != null) {
            req.setAttribute("error", errorMsg);
            req.setAttribute("sp", sp);
            req.getRequestDispatcher("/views/sanpham/form.jsp").forward(req, resp);
            return;
        }

        if (sp.getId() == 0) {
            repo.add(sp);
        } else {
            repo.update(sp);
        }

        resp.sendRedirect(req.getContextPath() + "/san-pham");
    }
}