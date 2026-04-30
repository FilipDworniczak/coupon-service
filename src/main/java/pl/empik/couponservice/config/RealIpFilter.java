package pl.empik.couponservice.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class RealIpFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var header = request.getHeader("X-Forwarded-For");
        var clientIp = header != null && !header.isBlank()
                ? header.split(",")[0].trim()
                : request.getRemoteAddr();
        request.setAttribute("clientIp", clientIp);
        log.debug("Client IP: {}", clientIp);

        filterChain.doFilter(request, response);
    }
}