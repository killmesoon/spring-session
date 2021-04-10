package com;

import com.config.SecurityConfig;
import com.config.SpringSessionConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.spi.inject.Inject;
import com.sun.jersey.spi.resource.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;
@Slf4j
public class Main {

    public static void main(String[] args) {

        final int PORT = 8080;
        //初始化一个jetty
        //jetty servlet容器
        Server server = new Server(PORT);

        //jersey的配置
        ServletHolder servlet = new ServletHolder(ServletContainer.class);
        // 设置初始化参数
        servlet.setInitParameter("com.sun.jersey.config.property.resourceConfigClass", "com.sun.jersey.api.core.PackagesResourceConfig");
        servlet.setInitParameter("com.sun.jersey.config.property.packages", "com");
        servlet.setInitParameter("com.sun.jersey.api.json.POJOMappingFeature", "true"); // 自动将对象映射成json返回


        ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SECURITY | ServletContextHandler.SESSIONS);
        handler.setContextPath("/");
        AnnotationConfigWebApplicationContext apiContext = new AnnotationConfigWebApplicationContext();
        apiContext.register(SpringSessionConfig.class);
        handler.addEventListener(new ContextLoaderListener(apiContext));
        handler.addServlet(servlet, "/*");
        handler.setInitParameter("contextClass", AnnotationConfigWebApplicationContext.class.getName());
        handler.addFilter(new FilterHolder(new DelegatingFilterProxy("springSessionRepositoryFilter")), "/*", EnumSet.of(DispatcherType.REQUEST));
        handler.addFilter(new FilterHolder(new DelegatingFilterProxy("springSecurityFilterChain")), "/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(handler);

        try {
            server.join();
            log.info("server start at " + PORT);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
