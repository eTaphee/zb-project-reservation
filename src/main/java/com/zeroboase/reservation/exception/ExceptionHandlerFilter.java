package com.zeroboase.reservation.exception;

import static com.zeroboase.reservation.exception.ErrorCode.INTERNAL_ERROR;
import static com.zeroboase.reservation.exception.ErrorCode.USERNAME_NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * filter 예외 처리기
 */
@Slf4j
@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (ReservationException e) {
            log.error("{} is occurred.", e.getErrorCode(), e);
            setErrorResponse(response, e);
        } catch (UsernameNotFoundException e) {
            log.error("UsernameNotFoundException is occurred.", e);
            setErrorResponse(response, new ReservationException(USERNAME_NOT_FOUND));
        } catch (Exception e) {
            log.error("Exception is occurred.", e);
            setErrorResponse(response, new ReservationException(INTERNAL_ERROR));
        }
    }

    private static void setErrorResponse(HttpServletResponse response, ReservationException e) {
        response.setStatus(e.getErrorCode().getStatus());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = ErrorResponse.from(e.getErrorCode());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        } catch (IOException ex) {
            log.error("IOException is occurred.", e);
        }
    }
}
