<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập - Lab 7</title>
    <style>
        :root { --ink: #17232f; --muted: #607080; --accent: #e66b3d; --paper: #fffdf8; --line: #e5ddd2; }
        * { box-sizing: border-box; }
        body { font-family: Georgia, 'Times New Roman', serif; color: var(--ink); background: radial-gradient(circle at 15% 15%, #f8d9bd 0, transparent 28%), linear-gradient(135deg, #f4eee5, #d9e5df); display: flex; justify-content: center; align-items: center; min-height: 100vh; margin: 0; padding: 24px; }
        .login-card { background: var(--paper); padding: clamp(28px, 5vw, 48px); border: 1px solid rgba(255,255,255,.8); box-shadow: 0 22px 60px rgba(23,35,47,.16); width: min(100%, 430px); }
        .eyebrow { color: var(--accent); font: 700 12px Arial, sans-serif; letter-spacing: 2px; text-transform: uppercase; }
        h1 { font-size: clamp(30px, 6vw, 42px); line-height: 1.05; margin: 10px 0 12px; }
        .intro { color: var(--muted); line-height: 1.6; margin: 0 0 28px; }
        .form-group { margin-bottom: 18px; }
        .form-group label { display: block; margin-bottom: 7px; font: 700 13px Arial, sans-serif; }
        .form-group input { width: 100%; padding: 13px 14px; border: 1px solid var(--line); border-radius: 2px; color: var(--ink); background: #fff; font: 16px Arial, sans-serif; }
        .form-group input:focus { outline: 3px solid rgba(230,107,61,.2); border-color: var(--accent); }
        .btn-submit { width: 100%; padding: 14px; background: var(--ink); color: white; border: none; border-radius: 2px; cursor: pointer; font: 700 14px Arial, sans-serif; }
        .btn-submit:hover { background: var(--accent); }
        .alert-danger { background: #fff0eb; color: #a43e25; border-left: 4px solid var(--accent); padding: 12px 14px; margin-bottom: 20px; font: 14px Arial, sans-serif; }
    </style>
</head>
<body>

<div class="login-card">
    <div class="eyebrow">Lab 7 · EAUT</div>
    <h1>Chào mừng trở lại</h1>
    <p class="intro">Đăng nhập để tiếp tục quản lý dữ liệu học tập của bạn.</p>

    <c:if test="${param.error == 'unauthorized'}">
        <div class="alert-danger">Bạn cần đăng nhập để truy cập!</div>
    </c:if>
    <c:if test="${param.error == 'invalid'}">
        <div class="alert-danger">Tài khoản hoặc mật khẩu không đúng!</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/lab7-login" method="post">
        <div class="form-group">
            <label for="username">Tài khoản</label>
            <input type="text" id="username" name="username" autocomplete="username" required autofocus>
        </div>
        
        <div class="form-group">
            <label for="password">Mật khẩu</label>
            <input type="password" id="password" name="password" autocomplete="current-password" required>
        </div>

        <button type="submit" class="btn-submit">Đăng nhập vào hệ thống</button>
    </form>
</div>

</body>
</html>