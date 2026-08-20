<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Lab 7 - Hệ thống quản lý</title>
    <style>
        :root { --ink: #17232f; --muted: #607080; --accent: #e66b3d; }
        * { box-sizing: border-box; }
        body { font-family: Georgia, 'Times New Roman', serif; color: var(--ink); background: linear-gradient(135deg, #f4eee5, #d9e5df); min-height: 100vh; margin: 0; padding: 28px; }
        main { max-width: 960px; margin: 0 auto; padding: clamp(28px, 7vw, 80px) 0; }
        .eyebrow { color: var(--accent); font: 700 12px Arial, sans-serif; letter-spacing: 2px; text-transform: uppercase; }
        h1 { max-width: 620px; font-size: clamp(38px, 8vw, 76px); line-height: .98; margin: 14px 0 20px; }
        p { max-width: 530px; color: var(--muted); font-size: 18px; line-height: 1.6; }
        .actions { display: flex; flex-wrap: wrap; gap: 12px; margin-top: 32px; }
        a { display: inline-block; padding: 13px 18px; color: #fff; background: var(--ink); text-decoration: none; font: 700 14px Arial, sans-serif; }
        a:hover { background: var(--accent); }
    </style>
</head>
<body>
    <main>
        <div class="eyebrow">EAUT · Lab 7</div>
        <h1>Hệ thống quản lý học tập</h1>
        <p>Một không gian gọn gàng để quản lý sinh viên, lớp học, sách, sản phẩm và điểm số.</p>
        <div class="actions">
            <a href="${pageContext.request.contextPath}/lab7-login">Đăng nhập</a>
        </div>
    </main>
</body>
</html>