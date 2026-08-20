<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sinh viên</title>
</head>
<body>
    <h2>Thông tin chi tiết sinh viên</h2>

    <p><strong>ID:</strong> ${sv.id}</p>
    <p><strong>Mã SV:</strong> ${sv.maSinhVien}</p>
    <p><strong>Họ tên:</strong> ${sv.hoTen}</p>
    <p><strong>Email:</strong> ${sv.email}</p>
    <p><strong>Lớp:</strong> ${sv.lop}</p>

    <hr>
    <a href="${pageContext.request.contextPath}/sinh-vien?action=edit&id=${sv.id}">Chỉnh sửa</a> |
    <a href="${pageContext.request.contextPath}/sinh-vien">Quay lại danh sách</a>
</body>
</html>