package lk.ijse.dep11.todo.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PostFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        List<String> origins = List.of("http://localhost:1234","http://localhost:5500");
        String origin = req.getMethod();
        if(origin != null && origins.stream().anyMatch(o -> o.equalsIgnoreCase(origin))){
            res.setHeader("Access-Control-Allow-Origin", origin);
        }
        String method = req.getMethod();
        if(method.equalsIgnoreCase("OPTIONS")){

            res.setHeader("Access-Control-Allow-Headers","Content-Type");
            res.setHeader("Access-Control-Allow-Methods", "OPTIONS, GET, POST, PATCH, DELETE");

        }
        chain.doFilter(req,res);

    }
}
