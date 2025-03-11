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
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("color"),
                        rs.getString("description"),
                        category
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public void addProduct(Product product) {
        String query = "INSERT INTO products (name, price, quantity, color, description, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, product.getName());
            stmt.setDouble(2, product.getPrice());
            stmt.setInt(3, product.getQuantity());
            stmt.setString(4, product.getColor());
            stmt.setString(5, product.getDescription());
            stmt.setInt(6, product.getCategory().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int productId) {
        String checkQuery = "SELECT id FROM products WHERE id = ?";
        String deleteQuery = "DELETE FROM products WHERE id = ?";
        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {

            checkStmt.setInt(1, productId);
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next()) {
                deleteStmt.setInt(1, productId);
                deleteStmt.executeUpdate();
            } else {
                System.out.println("Không tìm thấy sản phẩm có ID: " + productId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    public List<Product> searchProducts(String keyword) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.*, c.name AS category_name FROM products p JOIN category c ON p.category_id = c.id " +
                "WHERE p.name LIKE ? OR CAST(p.price AS CHAR) LIKE ?";
        try (Connection conn = BaseRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Category category = new Category(rs.getInt("category_id"), rs.getString("category_name"));
                products.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("color"),
                        rs.getString("description"),
                        category
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
