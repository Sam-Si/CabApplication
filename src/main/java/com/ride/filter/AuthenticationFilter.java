package com.ride.filter;

import com.ride.constants.ResponseCodeMapping;
import com.ride.entity.UserDetails;
import com.ride.exception.TokenMissingException;
import com.ride.service.IUserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.UUID;

@Component
@Slf4j
public class AuthenticationFilter extends GenericFilterBean {

    @Autowired
    private IUserService userService;

    @SneakyThrows
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String path = req.getServletPath();
        final String token = extractToken(req);
        MDC.put("commonRequestIdentifier", token);

        //Enhancement- Different types of authenication then make use of factory design pattern

        if(path.contains("/signup") || path.contains("/login")){
            log.info("Signup or Login authentication is not required");
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            String key = req.getHeader("Authorization") == null ? "" : req.getHeader("Authorization");
            log.info("Trying key: " + key);
            if (key == null || !key.startsWith("Bearer ")) {  //TOD0
                throw new TokenMissingException(ResponseCodeMapping.TOKEN_NOT_FOUND.getCode(), ResponseCodeMapping.TOKEN_NOT_FOUND.getMessage());
            }

            String authToken = key.substring(7); //TODO remove hardcoded into constant file

            /**
             * Authenticate Token- valid or not, expired or not - assumptions for simplicity
             */

            UserDetails userDetails = userService.findByAuthToken(authToken.substring(0,9));
            if (!ObjectUtils.isEmpty(userDetails)) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                HttpServletResponse resp = (HttpServletResponse) servletResponse;
                String error = "Invalid Token";
                resp.reset();
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                servletResponse.setContentLength(error.length());
                servletResponse.getWriter().write(error);
            }
        }

    }
    private String extractToken(final HttpServletRequest request) {
        return UUID.randomUUID().toString().toUpperCase().replace("-", "");
    }
}
