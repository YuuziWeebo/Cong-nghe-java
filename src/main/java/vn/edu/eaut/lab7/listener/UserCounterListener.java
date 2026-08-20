package vn.edu.eaut.lab7.listener;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class UserCounterListener implements HttpSessionListener {
    private static final AtomicInteger activeSessions = new AtomicInteger(0);

    public static int getActiveSessions() {
        return activeSessions.get();
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        int current = activeSessions.incrementAndGet();
        se.getSession().getServletContext().setAttribute("activeUsers", current);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        int current = activeSessions.decrementAndGet();
        se.getSession().getServletContext().setAttribute("activeUsers", Math.max(0, current));
    }
}