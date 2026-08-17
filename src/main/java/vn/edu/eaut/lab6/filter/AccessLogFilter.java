package vn.edu.eaut.lab6.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// Filter này sẽ chặn tất cả các request trong ứng dụng (/*)
@WebFilter("/*")
public class AccessLogFilter implements Filter {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("[AccessLogFilter] Initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        // 1. Lấy URI
        String uri = req.getRequestURI();

        // 2. Lấy Method (GET, POST,...)
        String method = req.getMethod();

        // 3. Lấy tên user từ Session (nếu chưa đăng nhập thì ghi "Anonymous")
        HttpSession session = req.getSession(false);
        String username = "Anonymous";
        if (session != null && session.getAttribute("username") != null) {
            username = (String) session.getAttribute("username");
        }

        // 4. Lấy thời gian hiện tại
        String timestamp = LocalDateTime.now().format(formatter);

        // In log ra Console
        System.out.println(String.format("[ACCESS LOG] [%s] - Method: %s | URI: %s | User: %s",
                timestamp, method, uri, username));

        // Cho phép request tiếp tục xử lý
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("[AccessLogFilter] Destroyed");
    }
}