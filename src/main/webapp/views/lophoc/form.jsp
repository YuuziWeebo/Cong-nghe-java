<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Lớp Học</title>
</head>
<body>
    <h2>${lh != null ? "Cập nhật thông tin lớp học" : "Thêm lớp học mới"}</h2>

    <form method="post" action="${pageContext.request.contextPath}/lop-hoc">
        <input type="hidden" name="id" value="${lh.id}">

        <p>Mã lớp: <br><input name="maLop" value="${lh.maLop}" required></p>
        <p>Tên lớp: <br><input name="tenLop" value="${lh.tenLop}" required></p>
        <p>Cố vấn học tập: <br><input name="coVanHocTap" value="${lh.coVanHocTap}" required></p>
        <p>Số lượng sinh viên: <br><input type="number" name="soLuongSinhVien" value="${lh.soLuongSinhVien}"></p>

        <button type="submit">Lưu</button>
        <a href="${pageContext.request.contextPath}/lop-hoc">Hủy</a>
    </form>
</body>
</html>