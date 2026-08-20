<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Sinh Vien</title>
</head>
<body>
    <h2>${sv != null ? "Cập nhật sinh viên" : "Thêm mới sinh viên"}</h2>

    <form method="post" action="${pageContext.request.contextPath}/sinh-vien">
        <!-- Input ẩn chứa ID để phân biệt thêm mới (trống) hoặc sửa (có ID) -->
        <input type="hidden" name="id" value="${sv.id}">

        <p>
            Mã SV: <br>
            <input name="maSinhVien" value="${sv.maSinhVien}" required>
        </p>
        <p>
            Họ tên: <br>
            <input name="hoTen" value="${sv.hoTen}" required>
        </p>
        <p>
            Email: <br>
            <input name="email" value="${sv.email}" type="email">
        </p>
        <p>
            Lớp: <br>
            <input name="lop" value="${sv.lop}">
        </p>

        <button type="submit">Lưu</button>
        <a href="${pageContext.request.contextPath}/sinh-vien">Hủy</a>
    </form>
</body>
</html>