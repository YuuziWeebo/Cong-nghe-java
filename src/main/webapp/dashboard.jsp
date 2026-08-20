<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard - Quản Lý Lab 7</title>
    <style>
        :root { --ink: #17232f; --muted: #607080; --accent: #e66b3d; --paper: #fffdf8; --line: #e5ddd2; }
        * { box-sizing: border-box; }
        body {
            font-family: Georgia, 'Times New Roman', serif;
            color: var(--ink);
            background: radial-gradient(circle at 90% 8%, #f8d9bd 0, transparent 25%), linear-gradient(135deg, #f4eee5, #d9e5df);
            margin: 0;
            padding: clamp(18px, 4vw, 48px);
        }
        .container {
            max-width: 1060px;
            margin: 0 auto;
            background: var(--paper);
            padding: clamp(24px, 5vw, 52px);
            box-shadow: 0 22px 60px rgba(23,35,47,.14);
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            gap: 20px;
            border-bottom: 1px solid var(--line);
            padding-bottom: 22px;
            margin-bottom: 34px;
        }
        .header h1 { font-size: clamp(28px, 5vw, 48px); line-height: 1; margin: 0; }
        .user-info {
            color: var(--muted);
            font: 14px Arial, sans-serif;
            text-align: right;
        }
        .btn-logout {
            color: var(--accent);
            text-decoration: none;
            font-weight: bold;
            margin-left: 10px;
        }
        .grid-menu {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 12px;
        }
        .card {
            border: 1px solid var(--line);
            padding: 22px;
            transition: transform 0.2s, box-shadow 0.2s, border-color 0.2s;
            text-decoration: none;
            color: var(--ink);
            background: #fff;
        }
        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 12px 25px rgba(23,35,47,.1);
            border-color: var(--accent);
        }
        .card h3 {
            margin-top: 0;
            color: var(--accent);
        }
        .card p {
            margin-bottom: 0;
            font-size: 13px;
            color: var(--muted);
            line-height: 1.5;
        }
        @media (max-width: 620px) { .header { align-items: flex-start; flex-direction: column; } .user-info { text-align: left; } }
    </style>
</head>
<body>

<div class="container">
    <div class="header">
        <h1>Hệ thống quản lý</h1>
        
        <div class="user-info">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    Xin chào, <b>${sessionScope.user.fullName}</b>
                    (<a href="${pageContext.request.contextPath}/lab7-logout" class="btn-logout">Đăng xuất</a>)
                </c:when>
                <c:otherwise>
                    <span>Chưa đăng nhập</span> |
                    <a href="${pageContext.request.contextPath}/lab7-login" style="color: #007bff;">Đăng nhập</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <h2>Danh mục chức năng</h2>
    <div class="grid-menu">
        <a href="${pageContext.request.contextPath}/sinh-vien" class="card">
            <h3>Quản lý Sinh viên</h3>
            <p>Xem danh sách sinh viên, thêm mới, sửa, xóa và phân trang.</p>
        </a>

        <a href="${pageContext.request.contextPath}/sach" class="card">
            <h3>Quản lý Sách</h3>
            <p>Quản lý danh mục sách và thông tin chi tiết.</p>
        </a>

        <a href="${pageContext.request.contextPath}/san-pham" class="card">
            <h3>Quản lý Sản phẩm</h3>
            <p>Danh sách sản phẩm, đơn giá và mô tả.</p>
        </a>

        <a href="${pageContext.request.contextPath}/lop-hoc" class="card">
            <h3>Quản lý Lớp học</h3>
            <p>Danh sách các lớp học chuyên ngành.</p>
        </a>

        <a href="${pageContext.request.contextPath}/diem-sinh-vien" class="card">
            <h3>Quản lý Điểm</h3>
            <p>Bảng điểm tổng kết và tra cứu điểm sinh viên.</p>
        </a>

        <a href="${pageContext.request.contextPath}/gio-hang" class="card">
            <h3>Giỏ hàng Session</h3>
            <p>Thao tác thêm, sửa, xóa sản phẩm trong giỏ hàng.</p>
        </a>
    </div>
</div>

</body>
</html>