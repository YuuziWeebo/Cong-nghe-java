<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="vn.edu.eaut.lab10.model.User,vn.edu.eaut.lab10.model.Role" %>
<% User u=(User)session.getAttribute("currentUser"); if(u==null){response.sendRedirect("login.jsp");return;} %>
<!DOCTYPE html><html><head><title>Dashboard</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css"></head><body>
<div class="wrap"><nav>
<a href="${pageContext.request.contextPath}/dashboard.jsp">Dashboard</a>
<% if(u.getRole()==Role.ADMIN){ %><a href="${pageContext.request.contextPath}/admin/users">Quản lý User</a><%}%>
<% if(u.getRole()==Role.ADMIN||u.getRole()==Role.STAFF){ %><a href="${pageContext.request.contextPath}/staff/students">Sinh viên</a><a href="${pageContext.request.contextPath}/staff/books">Sách</a><a href="${pageContext.request.contextPath}/staff/products">Sản phẩm</a><%}%>
<a href="${pageContext.request.contextPath}/user/profile">Hồ sơ</a><a href="${pageContext.request.contextPath}/user/password">Đổi mật khẩu</a><a href="${pageContext.request.contextPath}/auth?action=logout">Đăng xuất</a>
</nav>
<h2>Dashboard</h2><p>Xin chào <b><%=u.getFullName()%></b> — vai trò: <b><%=u.getRole()%></b></p>
<div class="cards"><div class="card">3 module nghiệp vụ<br><b>Sinh viên / Sách / Sản phẩm</b></div><div class="card">Bảo vệ URL<br><b>Authentication + Authorization Filter</b></div><div class="card">JPA + Transaction<br><b>MySQL</b></div></div>
</div></body></html>