package ITSD.Controller;

import ITSD.Entity.CustomerOrder;
import ITSD.Entity.MenuCategory;
import ITSD.Entity.MenuItem;
import ITSD.Entity.OrderItem;
import ITSD.Repository.IRepository;
import ITSD.Repository.RepositoryIntPK;
import ITSD.Repository.RepositoryStringPK;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.awt.*;
import java.io.IOException;

@WebServlet(value = "/Order/AddItem")
public class OrderAddItem extends HttpServlet {
    IRepository<CustomerOrder> orderRepo;
    IRepository<MenuCategory> categoryRepo;
    IRepository<OrderItem> orderItemRepo;

    @Override
    public void init() {
        orderRepo = new RepositoryIntPK<>(CustomerOrder.class);
        categoryRepo = new RepositoryStringPK<>(MenuCategory.class);
        orderItemRepo = new RepositoryIntPK<>(OrderItem.class);
    }

    @Override
    public void destroy() {
        try {
            categoryRepo.close();
            orderRepo.close();
            orderItemRepo.close();
        } catch (Exception e) {
            // SQUELCH
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerOrder order = orderRepo.get(request.getParameter("id"));
        MenuCategory mc = categoryRepo.get(request.getParameter("cat"));
        request.setAttribute("order", order);
        request.setAttribute("category", mc);
        request.setAttribute("items", mc.getItems());
        request
                .getRequestDispatcher("/OrderAddItem.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String orderId = request.getParameter("id");
        CustomerOrder order = orderRepo.get(orderId);
        MenuCategory mc = categoryRepo.get(request.getParameter("cat"));
        String menuItemId = request.getParameter("menuItemId");
        int qty = Integer.parseInt(request.getParameter("qty"));
        for (MenuItem mi : mc.getItems()) {
            if (String.valueOf(mi.getId()).equals(menuItemId)) {
                addMenuItems(order, mi, qty);
                break;
            }
        }
        orderRepo.update(order);
        response.sendRedirect(request.getContextPath() + "/Order/Edit?id=" + orderId);
    }

    private void addMenuItems(CustomerOrder order, MenuItem mi, int qty) {
        if (qty <= 0)
            return;
        OrderItem oi = findMenuItem(order, mi);
        oi.setQuantity(oi.getQuantity() + qty);

        if (oi.getId() == 0) {
            orderItemRepo.insert(oi);
        } else {
            orderItemRepo.update(oi);
        }
    }

    private OrderItem findMenuItem(CustomerOrder order, MenuItem mi) {
        for (OrderItem oi : order.getItems()) {
            if (oi.getMenuItem().getId() == mi.getId()) {
                return oi;
            }
        }

        OrderItem oi = new OrderItem(order, mi, 0);
        order.getItems().add(oi);
        return oi;
    }
}