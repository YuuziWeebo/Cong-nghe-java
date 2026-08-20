<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý sản phẩm</title>
</head>
<body>
    <h2>Danh sách sản phẩm</h2>

    <form method="get" action="${pageContext.request.contextPath}/san-pham">
        <input name="keyword" value="${param.keyword}" placeholder="Tìm theo mã hoặc tên sản phẩm">
        <button type="submit">Tìm kiếm</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/san-pham?action=new">Thêm sản phẩm mới</a></p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Mã SP</th>
                <th>Tên sản phẩm</th>
                <th>Mô tả</th>
                <th>Giá (VNĐ)</th>
                <th>Số lượng</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="sp" items="${dsSanPham}">
                <tr>
                    <td>${sp.id}</td>
                    <td>${sp.maSanPham}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/san-pham?action=detail&id=${sp.id}">
                            ${sp.tenSanPham}
                        </a>
                    </td>
                    <td>${sp.moTa}</td>
                    <td>${sp.gia}</td>
                    <td>${sp.soLuong}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/san-pham?action=edit&id=${sp.id}">Sửa</a> |
                        <a href="${pageContext.request.contextPath}/san-pham?action=delete&id=${sp.id}" 
                           onclick="return confirm('Xóa sản phẩm này?')">Xóa</a>
                        <a href="${pageContext.request.contextPath}/gio-hang?action=add&productId=${sp.id}">Thêm vào giỏ</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <p><a href="${pageContext.request.contextPath}/index.jsp">Về trang chủ</a></p>
</body>
</html>