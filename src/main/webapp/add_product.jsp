<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, model.Category, repository.CategoryRepository" %>
<%
    CategoryRepository categoryRepo = new CategoryRepository();
    List<Category> categories = categoryRepo.getAllCategories();
%>
<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Thêm Sản Phẩm</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            width: 400px;
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            display: block;
            margin: 10px 0 5px;
        }
        input, select, textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            width: 100%;
            padding: 10px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Thêm Sản Phẩm</h2>
    <form action="add-product" method="post">
        <label>Tên sản phẩm:</label>
        <input type="text" name="name" required>

        <label>Giá:</label>
        <input type="number" name="price" min="10000000" required>

        <label>Số lượng:</label>
        <input type="number" name="quantity" min="1" required>

        <label>Màu sắc:</label>
        <select name="color">
            <option value="Đỏ">Đỏ</option>
            <option value="Xanh">Xanh</option>
            <option value="Đen">Đen</option>
            <option value="Trắng">Trắng</option>
            <option value="Vàng">Vàng</option>
        </select>

        <label>Mô tả:</label>
        <textarea name="description"></textarea>

        <label>Danh mục:</label>
        <select name="category">
            <% for (Category c : categories) { %>
            <option value="<%= c.getId() %>"><%= c.getName() %></option>
            <% } %>
        </select>

        <button type="submit">Thêm sản phẩm</button>
    </form>
</div>
</body>
</html>
