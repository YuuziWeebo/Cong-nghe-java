package vn.edu.eaut.lab7.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.edu.eaut.lab7.model.CartItem;
import vn.edu.eaut.lab7.model.SanPham;
import vn.edu.eaut.lab7.repository.SanPhamRepository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/gio-hang")
public class GioHangController extends HttpServlet {
    private final SanPhamRepository spRepo = new SanPhamRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        HttpSession session = req.getSession();

        // Lấy giỏ hàng từ Session, nếu chưa có thì tạo mới
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute("cart", cart);
        }

        if ("add".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            SanPham sp = spRepo.findById(productId);

            if (sp != null) {
                if (cart.containsKey(productId)) {
                    CartItem item = cart.get(productId);
                    item.setSoLuong(item.getSoLuong() + 1);
                } else {
                    cart.put(productId, new CartItem(sp, 1));
                }
            }
            resp.sendRedirect(req.getContextPath() + "/gio-hang");
            return;
        }

        if ("remove".equals(action)) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            cart.remove(productId);
            resp.sendRedirect(req.getContextPath() + "/gio-hang");
            return;
        }

        if ("clear".equals(action)) {
            cart.clear();
            resp.sendRedirect(req.getContextPath() + "/gio-hang");
            return;
        }

        // Tính tổng tiền giỏ hàng
        double tongTien = cart.values().stream().mapToDouble(CartItem::getThanhTien).sum();
        req.setAttribute("tongTien", tongTien);

        req.getRequestDispatcher("/views/giohang/index.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Cập nhật số lượng sản phẩm từ form
        HttpSession session = req.getSession();
        @SuppressWarnings("unchecked")
        Map<Integer, CartItem> cart = (Map<Integer, CartItem>) session.getAttribute("cart");

        if (cart != null) {
            int productId = Integer.parseInt(req.getParameter("productId"));
            int soLuong = Integer.parseInt(req.getParameter("soLuong"));

            if (soLuong > 0 && cart.containsKey(productId)) {
                cart.get(productId).setSoLuong(soLuong);
            } else if (soLuong <= 0) {
                cart.remove(productId);
            }
        }
        resp.sendRedirect(req.getContextPath() + "/gio-hang");
    }
}