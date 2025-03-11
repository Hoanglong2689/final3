package controller;

import repository.ProductRepository;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delete-product")
public class DeleteProductServlet extends HttpServlet {
    private final ProductRepository productRepository = new ProductRepository();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int productId = Integer.parseInt(req.getParameter("id"));
        productRepository.deleteProduct(productId);
        resp.sendRedirect("products?message=Xóa sản phẩm thành công");
    }
}
