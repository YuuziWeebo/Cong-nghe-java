<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List,vn.edu.eaut.lab10.model.*" %><% List<User> users=(List<User>)request.getAttribute("users");User edit=(User)request.getAttribute("editUser"); %>
<!DOCTYPE html><html><head><title>Quản lý User</title><link rel="stylesheet" href="${pageContext.request.contextPath}/assets/style.css"></head><body><div class="wrap">
<a href="../dashboard.jsp">← Dashboard</a><h2>Quản lý người dùng</h2>
<%if(request.getAttribute("error")!=null){%><p class="error"><%=request.getAttribute("error")%></p><%}%>
<form method="post" action="${pageContext.request.contextPath}/admin/users">
<input type="hidden" name="action" value="<%=edit==null?"create":"update"%>"><%if(edit!=null){%><input type="hidden" name="id" value="<%=edit.getId()%>"><%}%>
<label>Email</label><input type="email" name="email" required value="<%=edit==null?"":edit.getEmail()%>">
<label>Họ tên</label><input name="fullName" required value="<%=edit==null?"":edit.getFullName()%>">
<label>Mật khẩu (bỏ trống khi sửa để giữ nguyên)</label><input type="password" name="password" <%=edit==null?"required":""%>>
<label>Role</label><select name="role"><%for(Role role:Role.values()){%><option <%=edit!=null&&edit.getRole()==role?"selected":""%>><%=role%></option><%}%></select>
<%if(edit!=null){%><label><input type="checkbox" name="active" <%=edit.isActive()?"checked":""%> style="width:auto"> Đang hoạt động</label><%}%>
<button type="submit"><%=edit==null?"Thêm":"Cập nhật"%></button> <%if(edit!=null){%><a class="btn" href="users">Hủy</a><%}%>
</form>
<table><tr><th>ID</th><th>Email</th><th>Họ tên</th><th>Role</th><th>Trạng thái</th><th>Thao tác</th></tr>
<%for(User x:users){%><tr><td><%=x.getId()%></td><td><%=x.getEmail()%></td><td><%=x.getFullName()%></td><td><%=x.getRole()%></td><td><%=x.isActive()?"Hoạt động":"Đã khóa"%></td><td><a href="users?edit=<%=x.getId()%>">Sửa</a></td></tr><%}%></table>
</div></body></html>