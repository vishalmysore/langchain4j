package com.vishal.web.rails;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.*;
public class CustomHttpServletResponseWrapper extends HttpServletResponseWrapper {
    private final CharArrayWriter writer = new CharArrayWriter();

    public CustomHttpServletResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public PrintWriter getWriter() {
        // Return a PrintWriter that writes to the CharArrayWriter
        return new PrintWriter(writer);
    }

    public String getResponseBody() {
        // Return the captured response body as a string
        return writer.toString();
    }
}
