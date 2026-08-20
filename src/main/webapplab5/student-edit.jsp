<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cập nhật sinh viên</title>
</head>
<body>
<h2>Cập nhật thông tin sinh viên</h2>

<form action="${pageContext.request.contextPath}/students" method="post">
    <!-- Action phân biệt xử lý cập nhật -->
    <input type="hidden" name="action" value="update" />

    <!-- Mã SV giữ ẩn để gửi lên server khi submit -->
    <input type="hidden" name="id" value="${student.id}" />

    <label>Mã sinh viên (Không thể sửa):</label><br>
    <input type="text" value="${student.id}" disabled readonly style="background-color: #eef;" /><br><br>

    <label>Họ tên:</label><br>
    <input type="text" name="name" value="${student.name}" required /><br><br>

    <label>Lớp:</label><br>
    <input type="text" name="className" value="${student.className}" required /><br><br>

    <label>Email:</label><br>
    <input type="email" name="email" value="${student.email}" required /><br><br>

    <button type="submit">Cập nhật</button>
    <a href="${pageContext.request.contextPath}/students"><button type="button">Hủy</button></a>
</form>
</body>
</html>