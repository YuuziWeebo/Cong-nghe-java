package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.edu.eaut.lab7.model.SinhVien;
import vn.edu.eaut.lab7.repository.SinhVienRepository;

import java.io.IOException;

@WebServlet("/sinh-vien")
public class SinhVienController extends HttpServlet {
    private final SinhVienRepository repo = new SinhVienRepository();
    private static final int PAGE_SIZE = 5;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("new".equals(action)) {
            req.getRequestDispatcher("/views/sinhvien/form.jsp").forward(req, resp);
            return;
        }

        if ("edit".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("sv", repo.findById(id));
            req.getRequestDispatcher("/views/sinhvien/form.jsp").forward(req, resp);
            return;
        }

        if ("detail".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute("sv", repo.findById(id));
            req.getRequestDispatcher("/views/sinhvien/detail.jsp").forward(req, resp);
            return;
        }

        if ("delete".equals(action)) {
            int id = Integer.parseInt(req.getParameter("id"));
            repo.delete(id);
            resp.sendRedirect(req.getContextPath() + "/sinh-vien");
            return;
        }

        // --- XỬ LÝ HIỂN THỊ DANH SÁCH & PHÂN TRANG ---
        int page = 1;
        String pageStr = req.getParameter("page");
        if (pageStr != null && !pageStr.isBlank()) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException ignored) {}
        }

        String keyword = req.getParameter("keyword");
        if (keyword == null) keyword = "";

        // Tính toán tổng số trang
        int totalRecords = repo.count(keyword);
        int totalPages = (int) Math.ceil((double) totalRecords / PAGE_SIZE);
        if (totalPages == 0) totalPages = 1;

        // Giới hạn trang nằm trong khoảng hợp lệ
        if (page < 1) page = 1;
        if (page > totalPages) page = totalPages;

        // Đưa dữ liệu sang View JSP
        req.setAttribute("dsSinhVien", repo.findByPage(keyword, page, PAGE_SIZE));
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("keyword", keyword);

        req.getRequestDispatcher("/views/sinhvien/list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        
        String idStr = req.getParameter("id");
        int id = (idStr != null && !idStr.isBlank()) ? Integer.parseInt(idStr) : 0;
        
        String maSinhVien = req.getParameter("maSinhVien");
        String hoTen = req.getParameter("hoTen");
        String email = req.getParameter("email");
        String lop = req.getParameter("lop");

        SinhVien sv = new SinhVien(id, maSinhVien, hoTen, email, lop);

        if (sv.getId() == 0) {
            repo.add(sv);
        } else {
            repo.update(sv);
        }

        resp.sendRedirect(req.getContextPath() + "/sinh-vien");
    }
}