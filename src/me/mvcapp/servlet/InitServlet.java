package me.mvcapp.servlet;

import me.mvcapp.dao.factory.CustomerDAOFactory;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author AlbertRui
 * @create 2018-02-22 10:25
 */
public class InitServlet extends HttpServlet {
    @Override
    public void init() {
        CustomerDAOFactory.getInstance().setType("jdbc");
        InputStream inStream = getServletContext().getResourceAsStream("/WEB-INF/classes/switch.properties");
        Properties properties = new Properties();
        try {
            properties.load(inStream);
            String type = properties.getProperty("type");
            CustomerDAOFactory.getInstance().setType(type);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
