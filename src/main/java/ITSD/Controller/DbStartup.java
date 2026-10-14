package ITSD.Controller;

import ITSD.Repository.CustomerRepo;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

// This class was intended to remove the CRAZY LONG DELAY
// when WebUser first attempts to use the database ...
// but, in reality, it just moves the annoying delay from after the first user click
// to before the first page is displayed :(
//
// grrr....
//
@WebListener
public class DbStartup implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        try (CustomerRepo repo = new CustomerRepo()) {
        } catch (Exception ex) {
            IO.println("Startup:EXCEPTION:" + ex.getMessage());
        }
    }
}
