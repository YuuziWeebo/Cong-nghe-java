<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Sách</title>
</head>
<body>
    <h2>${sach != null ? "Cập nhật thông tin sách" : "Thêm sách mới"}</h2>

    <form method="post" action="${pageContext.request.contextPath}/sach">
        <input type="hidden" name="id" value="${sach.id}">

        <p>Mã sách: <br><input name="maSach" value="${sach.maSach}" required></p>
        <p>Tên sách: <br><input name="tenSach" value="${sach.tenSach}" required></p>
        <p>Tác giả: <br><input name="tacGia" value="${sach.tacGia}" required></p>
        <p>Nhà xuất bản: <br><input name="nhaXuatBan" value="${sach.nhaXuatBan}"></p>
        <p>Năm xuất bản: <br><input type="number" name="namXuatBan" value="${sach.namXuatBan}"></p>

        <button type="submit">Lưu</button>
        <a href="${pageContext.request.contextPath}/sach">Hủy</a>
    </form>
</body>
</html>