package ITSD.Controller;

import ITSD.Entity.MenuCategory;
import ITSD.Repository.IRepository;
import ITSD.Repository.RepositoryStringPK;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/MenuItem/List")
public class MenuItemList extends HttpServlet {
    IRepository<MenuCategory> categoryRepo;

    @Override
    public void init() {
        categoryRepo = new RepositoryStringPK<>(MenuCategory.class);
    }

    @Override
    public void destroy() {
        try {
            categoryRepo.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("categories", categoryRepo.getAll());
        request.getRequestDispatcher("/MenuItemList.jsp").forward(request, response);
    }
}
