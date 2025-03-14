package repository;

import model.Category;
import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.*, c.name AS category_name FROM products p JOIN category c ON p.category_id = c.id";

        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Category category = new Category(rs.getInt("category_id"), rs.getString("category_name"));
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("color"),
                        rs.getString("description"),
                        category
                );
                products.add(product);
                System.out.println("📌 Đã lấy sản phẩm: " + product.getName());
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi SQL: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("🔍 Tổng số sản phẩm lấy được: " + products.size());
        return products;
    }


    public void addProduct(Product product) {
        String query = "INSERT INTO products (name, price, quantity, color, description, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            System.out.println("🔍 Đang thêm sản phẩm: " + product.getName());
            System.out.println("➡ Giá: " + product.getPrice());
            System.out.println("➡ Số lượng: " + product.getQuantity());
            System.out.println("➡ Màu sắc: " + product.getColor());
            System.out.println("➡ Mô tả: " + product.getDescription());
            System.out.println("➡ Danh mục ID: " + product.getCategory().getId());

            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setString(4, product.getColor());
            stmt.setString(5, product.getDescription());
            stmt.setInt(6, product.getCategory().getId());

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Sản phẩm đã được thêm vào Database.");
            } else {
                System.out.println("❌ Không thể thêm sản phẩm.");
            }
        } catch (SQLException e) {
            System.out.println("❌ Lỗi khi thêm sản phẩm: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public boolean deleteProduct(int productId) {
        String checkQuery = "SELECT id FROM products WHERE id = ?";
        String deleteQuery = "DELETE FROM products WHERE id = ?";
        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            checkStmt.setInt(1, productId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                deleteStmt.setInt(1, productId);
                int rowsAffected = deleteStmt.executeUpdate();
                return rowsAffected > 0; // Trả về true nếu xóa thành công
            } else {
                System.out.println("Không tìm thấy sản phẩm có ID: " + productId);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }

    public Product getProductById(int id) {
        String query = "SELECT p.*, c.name AS category_name FROM products p JOIN category c ON p.category_id = c.id WHERE p.id = ?";
        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Category category = new Category(rs.getInt("category_id"), rs.getString("category_name"));
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("color"),
                        rs.getString("description"),
                        category
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Product> searchProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + name + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("color"),
                        rs.getString("description"),
                        new Category(rs.getInt("category_id"), "")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
    public List<Product> searchProductsByPrice(double price) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products WHERE price <= ?";

        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setDouble(1, price);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("color"),
                        rs.getString("description"),
                        new Category(rs.getInt("category_id"), "")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}