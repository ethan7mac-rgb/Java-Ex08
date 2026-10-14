package ITSD.Controller;

import ITSD.Entity.CustomerOrder;
import ITSD.Repository.IRepository;
import ITSD.Repository.RepositoryIntPK;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/Order/List")
public class OrderList extends HttpServlet {
    IRepository<CustomerOrder> orderRepo;

    @Override
    public void init() {
        orderRepo = new RepositoryIntPK<>(CustomerOrder.class);
    }

    @Override
    public void destroy() {
        try {
            orderRepo.close();
        } catch (Exception e) {
            // SQUELCH
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setAttribute("list", orderRepo.getAll());
        request
                .getRequestDispatcher("/OrderList.jsp")
                .forward(request, response);
    }
}