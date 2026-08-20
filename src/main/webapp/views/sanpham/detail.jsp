<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Chi tiết sản phẩm</title>
</head>
<body>
    <h2>Thông tin chi tiết sản phẩm</h2>

    <p><strong>ID:</strong> ${sp.id}</p>
    <p><strong>Mã SP:</strong> ${sp.maSanPham}</p>
    <p><strong>Tên sản phẩm:</strong> ${sp.tenSanPham}</p>
    <p><strong>Mô tả:</strong> ${sp.moTa}</p>
    <p><strong>Giá:</strong> ${sp.gia} VNĐ</p>
    <p><strong>Số lượng tồn kho:</strong> ${sp.soLuong}</p>

    <hr>
    <a href="${pageContext.request.contextPath}/san-pham?action=edit&id=${sp.id}">Chỉnh sửa</a> |
    <a href="${pageContext.request.contextPath}/san-pham">Quay lại danh sách</a>
</body>
</html>