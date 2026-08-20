<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Trang Dashboard - Hệ thống Quản lý Sinh viên</title>
</head>
<body>
<h2>Bảng điều khiển (Dashboard)</h2>

<p>Xin chào, <b>${sessionScope.username}</b> (${sessionScope.role})!</p>
<p>Thời gian đăng nhập: <b>${sessionScope.loginTime}</b></p>

<hr>

<h3>Thống kê tổng quan</h3>
<ul>
    <li>Tổng số sinh viên hiện có: <b>${totalStudents}</b></li>
</ul>

<h4>Số lượng sinh viên theo từng lớp:</h4>
<table border="1" cellpadding="8" cellspacing="0">
    <tr>
        <th>Tên lớp</th>
        <th>Số lượng sinh viên</th>
    </tr>
    <c:forEach var="entry" items="${countByClass}">
        <tr>
            <td>${entry.key}</td>
            <td>${entry.value}</td>
        </tr>
    </c:forEach>
</table>

<br><hr>

<h3>Chức năng liên kết</h3>
<ul>
    <li><a href="${pageContext.request.contextPath}/students">Quản lý danh sách sinh viên</a></li>
    <li><a href="${pageContext.request.contextPath}/logout">Đăng xuất</a></li>
</ul>

</body>
</html>