package ITSD.Controller;

import ITSD.Entity.CustomerOrder;
import ITSD.Entity.OrderItem;
import ITSD.Repository.IRepository;
import ITSD.Repository.RepositoryIntPK;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/Order/Edit")
public class OrderEdit extends HttpServlet {
    IRepository<CustomerOrder> orderRepo;
    IRepository<OrderItem> orderItemRepo;

    @Override
    public void init() {
        orderRepo = new RepositoryIntPK<>(CustomerOrder.class);
        orderItemRepo = new RepositoryIntPK<>(OrderItem.class);
    }

    @Override
    public void destroy() {
        try {
            orderRepo.close();
            orderItemRepo.close();
        } catch (Exception e) {
            // SQUELCH
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        // GET is called when the user selects a single Customer (to Edit)
        String id = request.getParameter("id");
        String error = null;
        CustomerOrder order = null;

        if (id == null || "0".equals(id)) {
            error = "Order ID not provided";
        } else {
            order = orderRepo.get(id);
            if (order == null)
                error = "Order ID not found";
        }

        if (order == null)
            order = new CustomerOrder();    // JSP needs something to work with ...
        else
            order.calculateTotals();

        request.setAttribute("order", order);
        if (error != null)
            request.setAttribute("error", error);

        request
                .getRequestDispatcher("/OrderEdit.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        CustomerOrder order = orderRepo.get(id);
        if (order == null) {
            request.setAttribute("order", new CustomerOrder());
            request.setAttribute("error", "Order not found");
            request.getRequestDispatcher("/OrderEdit.jsp").forward(request, response);
            return;
        }

        String tip = request.getParameter("tipPercent");
        if (tip != null && !tip.isBlank()) {
            try {
                order.setTipPercent(Integer.parseInt(tip));
            } catch (NumberFormatException ignored) {
            }
        }

        String tax = request.getParameter("taxPercent");
        if (tax != null && !tax.isBlank()) {
            try {
                order.setTaxPercent(Integer.parseInt(tax));
            } catch (NumberFormatException ignored) {
            }
        }

        for (OrderItem oi : order.getItems()) {
            String qtyParam = request.getParameter("qty_" + oi.getId());
            if (qtyParam == null) {
                continue;
            }
            int qty;
            try {
                qty = Integer.parseInt(qtyParam);
            } catch (NumberFormatException ex) {
                continue;
            }
            if (qty <= 0) {
                orderItemRepo.delete(oi.getId());
            } else {
                oi.setQuantity(qty);
                orderItemRepo.update(oi);
            }
        }

        int tipPercent = order.getTipPercent();
        int taxPercent = order.getTaxPercent();
        order = orderRepo.get(id);
        if (order != null) {
            order.setTipPercent(tipPercent);
            order.setTaxPercent(taxPercent);
            order.calculateTotals();
            orderRepo.update(order);
        }

        response.sendRedirect("Edit?id=" + id);
    }
}
