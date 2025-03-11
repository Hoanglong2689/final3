package controller;

import model.Category;
import model.Product;
import repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/add-product")
public class AddProductServlet extends HttpServlet {
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String color = req.getParameter("color");
        String description = req.getParameter("description");
        int categoryId = Integer.parseInt(req.getParameter("category"));

        Category category = new Category(categoryId, "");
        Product product = new Product(0, name, price, quantity, color, description, category);

        productRepository.addProduct(product);

        resp.sendRedirect("products?message=Thêm sản phẩm thành công");
    }
}
