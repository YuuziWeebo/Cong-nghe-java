<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Danh sách sinh viên</title>
</head>
<body>
<h2>Danh sách sinh viên</h2>

<p>Xin chào: <b>${sessionScope.username}</b> (${sessionScope.role}) |
   <a href="${pageContext.request.contextPath}/logout">Đăng xuất</a>
</p>

<!-- Form tìm kiếm -->
<form action="${pageContext.request.contextPath}/students" method="get" style="margin-bottom: 15px;">
    <input type="text" name="keyword" value="${keyword}" placeholder="Nhập họ tên sinh viên..." />
    <button type="submit">Tìm kiếm</button>
    <c:if test="${not empty keyword}">
        <a href="${pageContext.request.contextPath}/students"><button type="button">Xóa lọc</button></a>
    </c:if>
</form>

<!-- Nút Thêm sinh viên (Chỉ hiển thị cho ADMIN) -->
<c:if test="${sessionScope.role == 'ADMIN'}">
    <a href="${pageContext.request.contextPath}/student-form.jsp">Thêm sinh viên</a>
    <br><br>
</c:if>

<c:choose>
    <c:when test="${not empty students}">
        <table border="1" cellpadding="8" cellspacing="0">
            <tr>
                <th>Mã SV</th>
                <th>Họ tên</th>
                <th>Lớp</th>
                <th>Email</th>
                <c:if test="${sessionScope.role == 'ADMIN'}">
                    <th>Hành động</th>
                </c:if>
            </tr>
            <c:forEach var="sv" items="${students}">
                <tr>
                    <td>${sv.id}</td>
                    <td>${sv.name}</td>
                    <td>${sv.className}</td>
                    <td>${sv.email}</td>
                    <c:if test="${sessionScope.role == 'ADMIN'}">
                        <td>
                            <a href="${pageContext.request.contextPath}/students?action=edit&id=${sv.id}">Sửa</a>
                            |
                            <a href="${pageContext.request.contextPath}/students?action=delete&id=${sv.id}"
                               onclick="return confirm('Bạn có chắc chắn muốn xóa sinh viên ${sv.name} không?');">
                               Xóa
                            </a>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
        </table>
    </c:when>

    <c:otherwise>
        <p style="color: red; font-weight: bold;">Không tìm thấy sinh viên nào phù hợp với từ khóa "${keyword}".</p>
    </c:otherwise>
</c:choose>

</body>
</html>