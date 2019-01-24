package com.user;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.MarkerManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet(
        name = "actionServlet", urlPatterns = "/files"
)
public class ActionServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doGet(req, resp);

        String action = req.getParameter("action");
        if (action != null) {
            log.info("请求的Action{}", action);
            String contents = null;
            switch (action) {
                case "readFoo":
                    contents = readFile("../foo.bar", true);
                    break;
                case "readLicense":
                    contents = readFile("../LICENSE", true);;
                    break;
                default:
                    contents = "特殊的Action：" + action;
                    log.warn("特殊的Action：{}", action);
            }
            if (contents != null)
                resp.getWriter().write(contents);
        } else {
            log.error("没有传入Action");
            resp.getWriter().write("没有传入Action");
        }
    }

    protected String readFile(String fileName, boolean deleteWhenDone) {
        log.entry(fileName, deleteWhenDone);
        try
        {
            byte[] data = Files.readAllBytes(new File(fileName).toPath());
            log.info("Successfully read file {}.", fileName);
            return log.exit(new String(data));
        }
        catch(IOException e)
        {
            log.error(MarkerManager.getMarker("WROX_CONSOLE"),
                    "Failed to read file {}.", fileName, e);
            return null;
        }
    }
}
