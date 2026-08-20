<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý lớp học</title>
</head>
<body>
    <h2>Danh sách lớp học</h2>

    <form method="get" action="${pageContext.request.contextPath}/lop-hoc">
        <input name="keyword" value="${param.keyword}" placeholder="Tìm theo mã hoặc tên lớp">
        <button type="submit">Tìm kiếm</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/lop-hoc?action=new">Thêm lớp học mới</a></p>

    <table border="1" cellpadding="6" cellspacing="0">
        <thead>
            <tr>
                <th>ID</th>
                <th>Mã lớp</th>
                <th>Tên lớp</th>
                <th>Cố vấn học tập</th>
                <th>Sĩ số</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="lh" items="${dsLopHoc}">
                <tr>
                    <td>${lh.id}</td>
                    <td>${lh.maLop}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/lop-hoc?action=detail&id=${lh.id}">
                            ${lh.tenLop}
                        </a>
                    </td>
                    <td>${lh.coVanHocTap}</td>
                    <td>${lh.soLuongSinhVien}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/lop-hoc?action=edit&id=${lh.id}">Sửa</a> |
                        <a href="${pageContext.request.contextPath}/lop-hoc?action=delete&id=${lh.id}" 
                           onclick="return confirm('Xóa lớp học này?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <p><a href="${pageContext.request.contextPath}/index.jsp">Về trang chủ</a></p>
</body>
</html>