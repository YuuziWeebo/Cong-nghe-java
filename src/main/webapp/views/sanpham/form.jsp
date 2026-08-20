<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Sản Phẩm</title>
</head>
<body>
    <h2>${sp != null && sp.id > 0 ? "Cập nhật sản phẩm" : "Thêm sản phẩm mới"}</h2>

    <c:if test="${not empty error}">
        <p style="color: red; font-weight: bold;">${error}</p>
    </c:if>

    <form method="post" action="${pageContext.request.contextPath}/san-pham">
        <input type="hidden" name="id" value="${sp.id}">

        <p>Mã sản phẩm: <br><input name="maSanPham" value="${sp.maSanPham}" required></p>
        <p>Tên sản phẩm: <br><input name="tenSanPham" value="${sp.tenSanPham}" required></p>
        <p>Mô tả: <br><textarea name="moTa">${sp.moTa}</textarea></p>
        <p>Giá: <br><input type="number" step="0.01" name="gia" value="${sp.gia}" required></p>
        <p>Số lượng: <br><input type="number" name="soLuong" value="${sp.soLuong}" required></p>

        <button type="submit">Lưu</button>
        <a href="${pageContext.request.contextPath}/san-pham">Hủy</a>
    </form>
</body>
</html>