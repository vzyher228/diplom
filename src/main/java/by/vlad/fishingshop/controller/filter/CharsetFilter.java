package by.vlad.fishingshop.controller.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

@WebFilter(urlPatterns = {"/ApiController"}, initParams = {
        @WebInitParam(name = "characterEncoding", value = "UTF-8", description = "Encoding Param")})
public class CharsetFilter implements Filter {
    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) {
        encoding = filterConfig.getInitParameter("characterEncoding");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        resp.setCharacterEncoding(encoding);
        filterChain.doFilter(req, resp);
    }

    public void destroy() {
    }
}
