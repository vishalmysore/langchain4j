package com.vishal.web.rails;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;


@Component
@Order(3)
@Slf4j
public class HallucinationRail implements Filter, Rail{
    private final static Logger LOG = LoggerFactory.getLogger(HallucinationRail.class);

    @Override
    public void init(final FilterConfig filterConfig) {
        LOG.info("Initializing filter :{}", this);
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpReq1 = (HttpServletRequest) request;
        CustomHttpServletRequestWrapper req = new CustomHttpServletRequestWrapper(httpReq1);
        LOG.info(
                "Starting a transaction for req : {}",
                req.getRequestURI());

        try {
            applyRail(req);
        } catch (RailBreakException e) {
            LOG.error(e.getLocalizedMessage());
            throw new RuntimeException(e);
        }
        chain.doFilter(req, response);

        if (response instanceof HttpServletResponse) {
            CustomHttpServletResponseWrapper wrappedResponse = new CustomHttpServletResponseWrapper((HttpServletResponse) response);
            String responseBody = wrappedResponse.toString(); // Get the response body as string
            // Now you can parse the response body as needed
            System.out.println("Response Body: " + responseBody);
        }

        LOG.info(
                "Committing a transaction for req : {}",
                req.getRequestURI());
    }

    @Override
    public boolean applyRail(ServletRequest request) throws RailBreakException {
        LOG.info("Req started for NER");
        HttpServletRequest req = (HttpServletRequest) request;

        // Log request URL and method
        System.out.println("Request URL: " + req.getRequestURL());
        System.out.println("Request Method: " + req.getMethod());

        // Log request headers
        System.out.println("Request Headers:");
        req.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + ": " + req.getHeader(headerName)));

        // Log request body
        LOG.info("Request Body:");
        StringBuilder requestBody = new StringBuilder();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                requestBody.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //need to apply NER filtering here
        LOG.info(requestBody.toString());
        return false;
    }

    @Override
    public boolean applyRail(ServletResponse req) throws RailBreakException {
        return false;
    }

    @Override
    public void destroy() {
        LOG.warn("Destructing filter :{}", this);
    }
}
