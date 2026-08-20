<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết lớp học</title>
</head>
<body>
    <h2>Thông tin chi tiết lớp học</h2>

    <p><strong>ID:</strong> ${lh.id}</p>
    <p><strong>Mã lớp:</strong> ${lh.maLop}</p>
    <p><strong>Tên lớp:</strong> ${lh.tenLop}</p>
    <p><strong>Cố vấn học tập:</strong> ${lh.coVanHocTap}</p>
    <p><strong>Số lượng sinh viên:</strong> ${lh.soLuongSinhVien}</p>

    <hr>
    <a href="${pageContext.request.contextPath}/lop-hoc?action=edit&id=${lh.id}">Chỉnh sửa</a> |
    <a href="${pageContext.request.contextPath}/lop-hoc">Quay lại danh sách</a>
</body>
</html>