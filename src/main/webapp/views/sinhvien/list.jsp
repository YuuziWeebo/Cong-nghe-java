<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Danh sách sinh viên</title>
    <style>
        :root { --ink: #17232f; --muted: #607080; --accent: #e66b3d; --paper: #fffdf8; --line: #e5ddd2; }
        * { box-sizing: border-box; }
        body { font-family: Arial, sans-serif; color: var(--ink); background: #f4eee5; margin: 0; padding: 24px; }
        main { max-width: 1120px; margin: 0 auto; background: var(--paper); padding: clamp(20px, 4vw, 40px); box-shadow: 0 12px 35px rgba(23,35,47,.08); }
        h1 { font: 700 clamp(26px, 4vw, 38px) Georgia, serif; margin: 0 0 22px; }
        .toolbar { display: flex; flex-wrap: wrap; justify-content: space-between; gap: 12px; margin-bottom: 22px; }
        .search { display: flex; flex: 1 1 320px; gap: 8px; }
        input { min-width: 0; flex: 1; padding: 11px 12px; border: 1px solid var(--line); font-size: 15px; }
        button, .button { padding: 11px 15px; border: 0; background: var(--ink); color: #fff; text-decoration: none; font-weight: 700; cursor: pointer; }
        button:hover, .button:hover { background: var(--accent); }
        .table-wrap { overflow-x: auto; }
        table { width: 100%; border-collapse: collapse; min-width: 720px; }
        th, td { padding: 13px 12px; border-bottom: 1px solid var(--line); text-align: left; }
        th { color: var(--muted); font-size: 12px; text-transform: uppercase; letter-spacing: .6px; }
        tbody tr:hover { background: #fff5ef; }
        td a { color: var(--accent); font-weight: 700; text-decoration: none; }
        .empty { color: var(--muted); text-align: center; padding: 35px; }
        .pagination { display: flex; flex-wrap: wrap; gap: 6px; margin-top: 22px; }
        .pagination a, .pagination span { padding: 8px 12px; border: 1px solid var(--line); color: var(--ink); text-decoration: none; }
        .pagination .active { background: var(--ink); color: white; border-color: var(--ink); }
        .back { display: inline-block; margin-top: 26px; color: var(--muted); }
    </style>
</head>
<body>
<main>
    <h1>Quản lý sinh viên</h1>
    <div class="toolbar">
        <form class="search" method="get" action="${pageContext.request.contextPath}/sinh-vien">
            <input name="keyword" value="${keyword}" placeholder="Tìm theo tên hoặc lớp" aria-label="Tìm sinh viên">
            <button type="submit">Tìm kiếm</button>
        </form>
        <a class="button" href="${pageContext.request.contextPath}/sinh-vien?action=new">+ Thêm sinh viên</a>
    </div>

    <div class="table-wrap"><table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Mã SV</th>
                <th>Họ tên</th>
                <th>Email</th>
                <th>Lớp</th>
                <th>Thao tác</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${empty dsSinhVien}">
                    <tr><td class="empty" colspan="6">Chưa có sinh viên phù hợp.</td></tr>
                </c:when>
                <c:otherwise>
            <c:forEach var="sv" items="${dsSinhVien}">
                <tr>
                    <td>${sv.id}</td>
                    <td>${sv.maSinhVien}</td>
                    <td>${sv.hoTen}</td>
                    <td>${sv.email}</td>
                    <td>${sv.lop}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/sinh-vien?action=edit&id=${sv.id}">Sửa</a> |
                        <a href="${pageContext.request.contextPath}/sinh-vien?action=delete&id=${sv.id}" onclick="return confirm('Xóa sinh viên này?')">Xóa</a>
                    </td>
                </tr>
            </c:forEach>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
    </div>

    <!-- Thanh phân trang -->
    <div class="pagination">
        <!-- Nút Trước -->
        <c:if test="${currentPage > 1}">
            <c:url var="previousUrl" value="/sinh-vien"><c:param name="page" value="${currentPage - 1}"/><c:param name="keyword" value="${keyword}"/></c:url>
            <a href="${previousUrl}">Trang trước</a>
        </c:if>

        <!-- Danh sách số trang -->
        <c:forEach var="i" begin="1" end="${totalPages}">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <span class="active">${i}</span>
                </c:when>
                <c:otherwise>
                    <c:url var="pageUrl" value="/sinh-vien"><c:param name="page" value="${i}"/><c:param name="keyword" value="${keyword}"/></c:url>
                    <a href="${pageUrl}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <!-- Nút Sau -->
        <c:if test="${currentPage < totalPages}">
            <c:url var="nextUrl" value="/sinh-vien"><c:param name="page" value="${currentPage + 1}"/><c:param name="keyword" value="${keyword}"/></c:url>
            <a href="${nextUrl}">Trang sau</a>
        </c:if>
    </div>

</main>
<a class="back" href="${pageContext.request.contextPath}/dashboard.jsp">← Về dashboard</a>
</body>
</html>