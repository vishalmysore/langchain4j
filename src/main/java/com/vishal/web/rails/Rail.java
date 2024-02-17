package com.vishal.web.rails;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public interface Rail {
   boolean applyRail(ServletRequest req) throws RailBreakException;
}
