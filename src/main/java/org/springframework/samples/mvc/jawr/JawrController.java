package org.springframework.samples.mvc.jawr;

import net.jawr.web.context.ThreadLocalJawrContext;
import net.jawr.web.servlet.JawrBinaryResourceRequestHandler;
import net.jawr.web.servlet.JawrRequestHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class JawrController implements ServletContextAware, InitializingBean, ServletContextListener {

    private final static String CONFIG_LOCATION = "/jawr.properties";

    private JawrRequestHandler jsHandler;

    private JawrRequestHandler cssHandler;

    private JawrRequestHandler binaryHandler;

    private ServletContext context;

    @RequestMapping("/**/*.js")
    public void handleJs(HttpServletRequest request, HttpServletResponse response) throws Exception {
        jsHandler.processRequest(getPath(request), request, response);
    }

    @RequestMapping("/**/*.css")
    public void handleCss(HttpServletRequest request, HttpServletResponse response) throws Exception {
        cssHandler.processRequest(getPath(request), request, response);
    }

    @RequestMapping(value = {
            "/**/*.png", "/**/*.gif", "/**/*.jpeg", "/**/*.jpg",
            "/**/*.woff", "/**/*.woff2", "/**/*.eot", "/**/*.ttf" })
    public void handleBinary(HttpServletRequest request, HttpServletResponse response) throws Exception {
        binaryHandler.processRequest(getPath(request), request, response);
    }

    private String getPath(HttpServletRequest request) {
        return (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        jsHandler = new JawrRequestHandler(context, getInitParams("js"), null);
        binaryHandler = new JawrBinaryResourceRequestHandler(context, getInitParams("binary"), null);
        cssHandler = new JawrRequestHandler(context, getInitParams("css"), null);
    }

    private Map<String, Object> getInitParams(String type) {
        Map<String, Object> initParams = new HashMap<>(2);
        initParams.put("configLocation", CONFIG_LOCATION);
        initParams.put("type", type);
        return initParams;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ThreadLocalJawrContext.reset();
    }

    @Override
    public void setServletContext(ServletContext context) {
        this.context = context;
    }

}
