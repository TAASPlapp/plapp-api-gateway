package com.plapp.apigateway.security;

import com.plapp.apigateway.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static class MutableHttpServletRequest extends HttpServletRequestWrapper {
        // holds custom header and value mapping
        private final Map<String, String> customHeaders;

        public MutableHttpServletRequest(HttpServletRequest request){
            super(request);
            this.customHeaders = new HashMap<>();
        }

        public void putHeader(String name, String value){
            this.customHeaders.put(name, value);
        }

        public String getHeader(String name) {
            // check the custom headers first
            String headerValue = customHeaders.get(name);

            if (headerValue != null){
                return headerValue;
            }
            // else return from into the original wrapped object
            return ((HttpServletRequest) getRequest()).getHeader(name);
        }

        public Enumeration<String> getHeaderNames() {
            // create a set of the custom header names
            Set<String> set = new HashSet<String>(customHeaders.keySet());

            // now add the headers from the wrapped request object
            @SuppressWarnings("unchecked")
            Enumeration<String> e = ((HttpServletRequest) getRequest()).getHeaderNames();
            while (e.hasMoreElements()) {
                // add the names of the request headers into the list
                String n = e.nextElement();
                set.add(n);
            }

            // create an enumeration from the set and return
            return Collections.enumeration(set);
        }
    }

    @Component
    @Order(1)
    public static class MutableRequestFilter implements Filter {
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
            MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(request);
            filterChain.doFilter(mutableRequest, response);
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("MAKING IT MUTABLE");
            HttpServletRequest httpServletRequest = (HttpServletRequest)request;
            MutableHttpServletRequest mutableRequest = new MutableHttpServletRequest(httpServletRequest);
            chain.doFilter(mutableRequest, response);
        }
    }

    private final AuthenticationService authenticationService;

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/api/auth/**").permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .addFilter(new JWTAuthorizationFilter(authenticationManager(), authenticationService));
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .and();

    }


}
