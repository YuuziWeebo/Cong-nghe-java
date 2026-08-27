<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html><html><head><title>Đăng nhập</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css"></head>
<body><div class="wrap" style="max-width:420px"><h2>Đăng nhập hệ thống</h2>
<% if(request.getAttribute("error")!=null){ %><p class="error"><%=request.getAttribute("error")%></p><%}%>
<% if("1".equals(request.getParameter("seed"))){ %><p class="ok">Đã tạo dữ liệu mẫu.</p><%}%>
<form method="post" action="${pageContext.request.contextPath}/auth">
<label>Email</label><input type="email" name="email" required>
<label>Mật khẩu</label><input type="password" name="password" required>
<button type="submit">Đăng nhập</button></form>
<hr><small>Demo: admin@eaut.edu.vn / 123456</small><br><small>staff@eaut.edu.vn / 123456</small><br><small>user@eaut.edu.vn / 123456</small>
<p><a href="${pageContext.request.contextPath}/seed">Tạo dữ liệu mẫu lần đầu</a></p></div></body></html>