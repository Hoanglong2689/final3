package controller;

import model.Product;
import repository.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "products", value = "/products")
public class ProductServlet extends HttpServlet {
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String search = req.getParameter("search");
        List<Product> products;

        if (search != null && !search.isEmpty()) {
            try {
                double price = Double.parseDouble(search);
                products = productRepository.searchProductsByPrice(price);
            } catch (NumberFormatException e) {
                products = productRepository.searchProductsByName(search);
            }
        } else {
            products = productRepository.getAllProducts();
        }

        req.setAttribute("products", products);
        req.getRequestDispatcher("product_list.jsp").forward(req, resp);
}
}