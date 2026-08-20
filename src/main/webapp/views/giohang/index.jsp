<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giỏ hàng của bạn</title>
</head>
<body>
    <h2>Chi tiết giỏ hàng</h2>

    <c:choose>
        <c:when test="${empty sessionScope.cart || sessionScope.cart.size() == 0}">
            <p>Giỏ hàng của bạn đang trống!</p>
        </c:when>
        <c:otherwise>
            <table border="1" cellpadding="6" cellspacing="0">
                <thead>
                    <tr>
                        <th>Tên sản phẩm</th>
                        <th>Đơn giá (VNĐ)</th>
                        <th>Số lượng</th>
                        <th>Thành tiền (VNĐ)</th>
                        <th>Thao tác</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="entry" items="${sessionScope.cart}">
                        <c:set var="item" value="${entry.value}" />
                        <tr>
                            <td>${item.sanPham.tenSanPham}</td>
                            <td>${item.sanPham.gia}</td>
                            <td>
                                <form method="post" action="${pageContext.request.contextPath}/gio-hang" style="display:inline;">
                                    <input type="hidden" name="productId" value="${item.sanPham.id}">
                                    <input type="number" name="soLuong" value="${item.soLuong}" min="1" style="width: 50px;">
                                    <button type="submit">Cập nhật</button>
                                </form>
                            </td>
                            <td>${item.thanhTien}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/gio-hang?action=remove&productId=${item.sanPham.id}"
                                   onclick="return confirm('Xóa khỏi giỏ hàng?')">Xóa</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <p><strong>Tổng tiền thanh toán: </strong> <span style="color:red; font-size: 1.2em;">${tongTien} VNĐ</span></p>
            <p><a href="${pageContext.request.contextPath}/gio-hang?action=clear" onclick="return confirm('Xóa toàn bộ giỏ hàng?')">Xóa tất cả</a></p>
        </c:otherwise>
    </c:choose>

    <hr>
    <p>
        <a href="${pageContext.request.contextPath}/san-pham">Tiếp tục mua sắm</a> | 
        <a href="${pageContext.request.contextPath}/index.jsp">Về trang chủ</a>
    </p>
</body>
</html>