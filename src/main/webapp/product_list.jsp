`<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Product" %>
<jsp:useBean id="products" scope="request" type="java.util.List<model.Product>" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Quản Lý Sản Phẩm</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #f4f4f4;
        }
        h2 {
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        button {
            padding: 8px 12px;
            border: none;
            cursor: pointer;
            border-radius: 5px;
        }
        .add-btn {
            background-color: #28a745;
            color: white;
            position: absolute;
            top: 20px;
            right: 20px;
        }
        .edit-btn {
            background-color: #007bff;
            color: white;
        }
        .delete-btn {
            background-color: #dc3545;
            color: white;
        }
        .search-box {
            text-align: center;
            margin-bottom: 20px;
        }
        .search-box input {
            padding: 8px;
            width: 300px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .search-box button {
            background-color: #007bff;
            color: white;
            padding: 8px 12px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
    <script>
        function confirmDelete(id) {
            if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
                window.location.href = 'delete-product?id=' + id;
            }
        }
    </script>
</head>
<body>
<h2>Danh sách sản phẩm</h2>

<%-- Hiển thị thông báo (nếu có) --%>
<%
    String message = request.getParameter("message");
    if (message != null && !message.isEmpty()) {
%>
<p style="color: green; text-align: center;"><%= message %></p>
<%
    }
%>

<%-- Form tìm kiếm --%>
<div class="search-box">
    <form action="products" method="get">
        <input type="text" name="search" placeholder="Nhập tên sản phẩm hoặc giá">
        <button type="submit">Tìm kiếm</button>
    </form>
</div>

<%-- Nút thêm mới --%>
<button class="add-btn" onclick="window.location.href='add_product.jsp'">Thêm mới</button>

<%-- Bảng hiển thị danh sách sản phẩm --%>
<table>
    <tr>
        <th>Tên</th>
        <th>Giá</th>
        <th>Số lượng</th>
        <th>Màu sắc</th>
        <th>Mô tả</th>
        <th>Danh mục</th>
        <th>Hành động</th>
    </tr>
    <%
        if (products != null && !products.isEmpty()) {
            for (Product p : products) {
    %>
    <tr id="product-<%= p.getId() %>">
        <td><%= p.getName() %></td>
        <td><fmt:formatNumber value="${p.price}" type="number" pattern="###,###" /></td>
        <td><%= p.getQuantity() %></td>
        <td><%= p.getColor() %></td>
        <td><%= p.getDescription() %></td>
        <td><%= p.getCategory().getName() %></td>
        <td>
            <button class="edit-btn" onclick="window.location.href='edit_product.jsp?id=<%= p.getId() %>'">Sửa</button>
            <button class="delete-btn" onclick="confirmDelete(<%= p.getId() %>)">Xóa</button>
        </td>
    </tr>
    <%
        }
    } else {
    %>
    <tr>
        <td colspan="7" style="text-align: center; color: red;">Không có sản phẩm nào</td>
    </tr>
    <%
        }
    %>
</table>
<script>
    function confirmDelete(id) {
        if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này không?")) {
            fetch('delete-product', {
                method: 'POST',
                headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
                body: 'id=' + id
            })
                .then(response => response.json())
                .then(data => {
                    if (data.success) {
                        alert("Xóa sản phẩm thành công!");
                        location.reload(); // Tải lại trang để cập nhật danh sách
                    } else {
                        alert("Xóa sản phẩm thất bại!");
                    }
                })
                .catch(error => console.error("Lỗi khi xóa sản phẩm:", error));
        }
    }
</script>
</body>
</html>`