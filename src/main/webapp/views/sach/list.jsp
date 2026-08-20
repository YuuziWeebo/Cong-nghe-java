<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý sách</title>
</head>
<body>
    <h2>Danh sách sách</h2>

    <form method="get" action="${pageContext.request.contextPath}/sach">
        <input name="keyword" value="${param.keyword}" placeholder="Tìm theo tên sách hoặc tác giả">
        <button type="submit">Tìm kiếm</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/sach?action=new">Thêm sách mới</a></p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Mã sách</th>
                <th>Tên sách</th>
                <th>Tác giả</th>
                <th>NXB</th>
                <th>Năm XB</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="s" items="${dsSach}">
                <tr>
                    <td>${s.id}</td>
                    <td>${s.maSach}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/sach?action=detail&id=${s.id}">
                            ${s.tenSach}
                        </a>
                    </td>
                    <td>${s.tacGia}</td>
                    <td>${s.nhaXuatBan}</td>
                    <td>${s.namXuatBan}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/sach?action=edit&id=${s.id}">Sửa</a> |
                        <a href="${pageContext.request.contextPath}/sach?action=delete&id=${s.id}" 
                           onclick="return confirm('Xóa sách này?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <p><a href="${pageContext.request.contextPath}/index.jsp">Về trang chủ</a></p>
</body>
</html>