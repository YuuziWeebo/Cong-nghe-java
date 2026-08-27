package vn.edu.eaut.lab10.listener;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebListener;
import vn.edu.eaut.lab10.config.JPAUtil;

@WebListener
public class AppListener implements ServletContextListener {
    public void contextDestroyed(ServletContextEvent e){ JPAUtil.shutdown(); }
}
