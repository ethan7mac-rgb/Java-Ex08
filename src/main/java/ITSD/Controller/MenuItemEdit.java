package ITSD.Controller;

import ITSD.Entity.MenuItem;
import ITSD.Repository.IRepository;
import ITSD.Repository.RepositoryIntPK;
import ITSD.Repository.RepositoryStringPK;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import ITSD.Entity.MenuCategory;

@WebServlet(value = "/MenuItem/Edit")
public class MenuItemEdit extends HttpServlet {
    IRepository<MenuItem> menuItemRepo;
    IRepository<MenuCategory> categoryRepo;

    @Override
    public void init() {
        menuItemRepo = new RepositoryIntPK<>(MenuItem.class);
        categoryRepo = new RepositoryStringPK<>(MenuCategory.class);
    }

    @Override
    public void destroy() {
        try {
            menuItemRepo.close();
            categoryRepo.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        MenuItem item = menuItemRepo.get(request.getParameter("id"));
        if (item == null) {
            item = new MenuItem();
        }
        item.setCategory(categoryRepo.get(request.getParameter("cat")));

        request.setAttribute("mi", item);
        request.setAttribute("categoryList", categoryRepo.getAll());
        request.getRequestDispatcher("/MenuItemEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MenuItem item = menuItemRepo.get(request.getParameter("id"));
        if (item == null) {
            item = new MenuItem();
        }

        item.setDescription(request.getParameter("description"));
        item.setPrice(Double.parseDouble(request.getParameter("price")));
        item.setVegetarian(request.getParameter("vegetarian") != null);
        item.setCategory(categoryRepo.get(request.getParameter("categoryID")));

        if (item.getId() == 0) {
            menuItemRepo.insert(item);
        } else {
            menuItemRepo.update(item);
        }

        response.sendRedirect(request.getContextPath() + "/MenuItem/List");
    }
}
