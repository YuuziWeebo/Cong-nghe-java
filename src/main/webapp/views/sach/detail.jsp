<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sách</title>
</head>
<body>
    <h2>Thông tin chi tiết sách</h2>

    <p><strong>ID:</strong> ${sach.id}</p>
    <p><strong>Mã sách:</strong> ${sach.maSach}</p>
    <p><strong>Tên sách:</strong> ${sach.tenSach}</p>
    <p><strong>Tác giả:</strong> ${sach.tacGia}</p>
    <p><strong>Nhà xuất bản:</strong> ${sach.nhaXuatBan}</p>
    <p><strong>Năm xuất bản:</strong> ${sach.namXuatBan}</p>

    <hr>
    <a href="${pageContext.request.contextPath}/sach?action=edit&id=${sach.id}">Chỉnh sửa</a> |
    <a href="${pageContext.request.contextPath}/sach">Quay lại danh sách</a>
</body>
</html>